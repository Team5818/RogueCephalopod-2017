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
package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Constants;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Trajectory;
import org.usfirst.frc.team5818.robot.utils.TrajectoryFollower;
import org.usfirst.frc.team5818.robot.utils.TrajectoryGenerator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This controller spins the drive train using a profile.
 */
public class SpinWithProfile extends Command {

    private static final double minDeltaHeading = 2;

    private DriveTrain driveTrain;
    private double goalHeading;
    private TrajectoryFollower followerLeft;
    private TrajectoryFollower followerRight;
    boolean stoppingAtEnd;
    boolean fieldCentered;
    double angle;
    double kTurn = 1.5 / Math.PI;
    double kSmall = .5 / Math.PI;
    int loopCount;

    public SpinWithProfile(double ang, boolean field, boolean stop) {
        setTimeout(1.5);
        driveTrain = Robot.runningRobot.driveTrain;
        requires(driveTrain);
        angle = ang;
        fieldCentered = field;
        stoppingAtEnd = stop;
    }

    protected void initialize() {
        loopCount = 0;
        double curHeading = driveTrain.getGyroHeading();
        if (fieldCentered) {
            goalHeading = angle;
        } else {
            goalHeading = angle + curHeading;
        }

        double deltaHeading = goalHeading - curHeading;
        double distance = Math.abs(deltaHeading * Constants.Constant.wheelToWheelWidth() / 2);

        Trajectory leftProfile = TrajectoryGenerator.generate(.5 * Constants.Constant.maxVelocityIPS(),
                .8 * Constants.Constant.maxAccelIPS2(), .02, 0.0, curHeading, Math.abs(distance), 0.0, goalHeading);
        Trajectory rightProfile = leftProfile.copy();

        if (Math.abs(deltaHeading) > Math.toRadians(minDeltaHeading)) {
            if (deltaHeading > 0) {
                leftProfile.scale(-1.0);
                rightProfile.scale(1.0);
            } else {
                leftProfile.scale(1.0);
                rightProfile.scale(-1.0);
            }
        }

        followerLeft = new TrajectoryFollower(.06, 1.0 / Constants.Constant.maxVelocityIPS(),
                0.3 / Constants.Constant.maxAccelIPS2(), leftProfile, Side.LEFT);
        followerRight = new TrajectoryFollower(.06, 1.0 / Constants.Constant.maxVelocityIPS(),
                0.3 / Constants.Constant.maxAccelIPS2(), rightProfile, Side.RIGHT);

        reset();
    }

    public void reset() {
        followerLeft.reset();
        followerRight.reset();
        driveTrain.resetEncs();
    }

    public int getFollowerCurrentSegment() {
        return followerLeft.getCurrentSegment();
    }

    public int getNumSegments() {
        return followerLeft.getNumSegments();
    }

    protected void execute() {
        loopCount++;
        double distanceL = driveTrain.getLeftSide().getSidePosition();
        double distanceR = driveTrain.getRightSide().getSidePosition();

        double speedLeft = followerLeft.calculate(-distanceL);
        double speedRight = followerRight.calculate(-distanceR);

        double goalHeading = followerLeft.getHeading();
        double observedHeading = driveTrain.getGyroHeading();

        double angleDiffRads = MathUtil.wrapAngleRad(goalHeading - observedHeading);

        double turn;
        if (followerLeft.isFinishedTrajectory()) {
            turn = kSmall * angleDiffRads;
        } else {
            turn = kTurn * angleDiffRads;
        }
        driveTrain.setPowerLeftRight(speedLeft - turn, speedRight + turn);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(goalHeading - driveTrain.getGyroHeading()) < .05 || isTimedOut();
    }

    @Override
    protected void end() {
        if (stoppingAtEnd) {
            driveTrain.stop();
        }
    }
}
