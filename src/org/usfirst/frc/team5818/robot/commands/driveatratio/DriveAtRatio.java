/*
 * This file is part of Rogue-Cephalopod, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usfirst.frc.team5818.robot.commands.driveatratio;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import java.util.function.Consumer;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive at ratio routine. Uses various options to send ratios to the drive
 * train, controlling where the robot goes.
 */
public class DriveAtRatio extends Command {

	public static final double MIN_SONIC_RANGE = 5;
	private static final double CAMERA_FOV = Constant.cameraFov();

	public static DriveAtRatio withDeadReckon(Consumer<DeadReckonOpts.Builder> config) {
		DeadReckonOpts.Builder b = DeadReckonOpts.builder();
		config.accept(b);
		return new DriveAtRatio(b.build());
	}

	public static DriveAtRatio withProfile(Consumer<ProfileOpts.Builder> config) {
		ProfileOpts.Builder b = ProfileOpts.builder();
		config.accept(b);
		return new DriveAtRatio(b.build());
	}

	public static DriveAtRatio withVision(Camera camera, Consumer<VisionOpts.Builder> config) {
		VisionOpts.Builder b = VisionOpts.builder(camera);
		config.accept(b);
		return new DriveAtRatio(b.build());
	}

	public static DriveAtRatio withSpin(Consumer<SpinOpts.Builder> config) {
		SpinOpts.Builder b = SpinOpts.builder();
		config.accept(b);
		return new DriveAtRatio(b.build());
	}

	private double inches;
	private double maxPow;
	private double leftPowMult;
	private double rightPowMult;
	private double leftVel;
	private double rightVel;
	private Vector2d avStart;
	private double targetRatio;
	private boolean stopAtEnd;
	private int camMultiplier;
	private boolean useVision;
	private double maxRatio;
	private CameraController cont;
	private Camera camera;
	private boolean useSpin;
	private Spin spinSide;
	private double leftSpinMult;
	private double rightSpinMult;
	private double powerSlope;
	private double minPower;
	private boolean isProfiling;
	private double visOffset;

	private DriveAtRatio(DriveAtRatioOptions opts) {
		visOffset = opts.getVisionOffset();
		isProfiling = opts.isProfiling();
		minPower = opts.getMinPower();
		powerSlope = opts.getAccel();
		useSpin = opts.isSpinning();
		spinSide = opts.getRotation();
		camera = opts.getCamera();
		inches = opts.getInches();
		maxPow = opts.getMaxPower();
		requires(Robot.runningRobot.driveTrain);
		cont = Robot.runningRobot.camCont;
		requires(cont);
		if (maxPow > .2) {
			setTimeout(inches / 12);
		}
		targetRatio = opts.getTargetRatio(); // Ratio is LEFT/RIGHT
		maxRatio = opts.getMaxRatio();
        switch (camera) {
            case NONE:
                maxPow = Math.abs(maxPow);
                camMultiplier = 0;
                useVision = false;
                break;
            case CAM_GEARS:
                camMultiplier = 1;
                maxPow = -Math.abs(maxPow);
                useVision = true;
                break;
            case CAM_TAPE:
                camMultiplier = -1;
                maxPow = Math.abs(maxPow);
                useVision = true;
                break;
        }

		if (spinSide == Spin.CLOCKWISE) {
			leftSpinMult = -1;
			rightSpinMult = 1;
		} else if (spinSide == Spin.COUNTERCW) {
			leftSpinMult = 1;
			rightSpinMult = -1;
		} else {
			leftSpinMult = 1;
			rightSpinMult = 1;
		}

		stopAtEnd = opts.isStoppingAtEnd();
	}

	@Override
	public void initialize() {
		SmartDashboard.putNumber("Vision Angle", Robot.runningRobot.vision.getCurrentAngle());
		leftPowMult = 1;
		rightPowMult = 1;
		if (useSpin) {
			avStart = Robot.runningRobot.driveTrain.getDistance().abs();
		} else {
			avStart = Robot.runningRobot.driveTrain.getDistance();
		}

		if (camera.equals(Camera.CAM_TAPE)) {
			cont.enterTapeMode();
		} else if (camera.equals(Camera.CAM_GEARS)) {
			cont.enterGearMode();
		}
	}

	@Override
	public void execute() {
		leftVel = Math.abs(Robot.runningRobot.driveTrain.left.getSideVelocity());
		rightVel = Math.abs(Robot.runningRobot.driveTrain.right.getSideVelocity());
		Vector2d distance = Robot.runningRobot.driveTrain.getDistance();
		double currRatio = targetRatio;

		if (leftVel != 0 && rightVel != 0) {
			currRatio = leftVel / rightVel;
		}

		double anglePower = 0.0;
		double visAng = Robot.runningRobot.vision.getCurrentAngle();
		if (!Double.isNaN(visAng)) {
			anglePower = (visAng + visOffset) / CAMERA_FOV * camMultiplier * 2.0;
		}

		double target = targetRatio;

		if (useVision) {
			target = Math.pow(maxRatio, anglePower);
		} else {
			target = Math.pow(target, Math.signum(maxPow));
		}

		leftPowMult = 1.0;
		rightPowMult = currRatio / Math.pow(target, 1);

		Vector2d driveVec = new Vector2d(leftSpinMult * leftPowMult, rightSpinMult * rightPowMult);
		if (isProfiling) {
			driveVec = driveVec.normalize(MathUtil.absMin(minPower + powerSlope * distance.average(), maxPow));
		} else {
			driveVec = driveVec.normalize(maxPow);
		}

		SmartDashboard.putNumber("PowerLeft", driveVec.getX());
		SmartDashboard.putNumber("PowerRight", driveVec.getY());
		Robot.runningRobot.driveTrain.setPowerLeftRight(driveVec);

	}

	@Override
	public void end() {
		if (stopAtEnd) {
			Robot.runningRobot.driveTrain.stop();
		}
	}

	@Override
	protected boolean isFinished() {
		final DriveTrain dt = Robot.runningRobot.driveTrain;
		Vector2d distance;
		boolean passedTarget;

		double absIn = Math.abs(inches);
		if (useSpin) {
			distance = dt.getDistance().abs();
			passedTarget = Math.abs(distance.getX() - avStart.getX()) >= absIn
					|| Math.abs(distance.getY() - avStart.getY()) >= absIn;
		} else {
			distance = dt.getDistance();
			passedTarget = Math.abs(distance.average() - avStart.average()) >= absIn;
		}

		return isTimedOut() || passedTarget;
	}

}
