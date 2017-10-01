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
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagicSpinToVision extends Command {

    private static final double INCHES_PER_ROTATION = 100.0;
    private DriveTrain dt;
    private VisionTracker vis;
    private double angle;

    public MagicSpinToVision() {
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        vis = Robot.runningRobot.vision;
        setTimeout(1);
    }

    @Override
    protected void initialize() {
        dt.getLeftSide().positionControl();
        dt.getRightSide().slaveToOtherSide(true);
        dt.resetEncs();
        double ang = vis.getCurrentAngle();
        if(!Double.isNaN(ang)) {
            angle = dt.getGyroHeading() + Math.toRadians(ang);
         }
        else {
            angle = 0.0;
        }
    }

    @Override
    protected void execute() {
        double diff = MathUtil.wrapAngleRad(angle - dt.getGyroHeading());
        DriverStation.reportError("" + diff, false);
        double dist = diff / (2.0 * Math.PI) * INCHES_PER_ROTATION;
        SmartDashboard.putNumber("spin dist", dist);
        DriverStation.reportError("" + dist, false);
        dt.getLeftSide().driveDistanceNoReset(dt.getLeftSide().getSidePosition() + dist, 200, 200);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(MathUtil.wrapAngleRad(angle - dt.getGyroHeading())) < .03
                && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2 || isTimedOut();
    }

    @Override
    protected void end() {
        dt.stop();
    }

}
