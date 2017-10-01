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
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Control command for driving.
 */
public class DriveControlCommand extends ControlCommand {

    private final DriveTrain driveTrain = Robot.runningRobot.driveTrain;
    private Joystick throttle;
    private Joystick turning;

    public DriveControlCommand(Joystick throt, Joystick turn) {
        super(throt, turn);
        throttle = throt;
        turning = turn;
        requires(driveTrain);
    }

    @Override
    protected void setPower() {
        Vector2d driveVector = Vectors.fromJoystick(throttle, turning, true);
        driveVector = MathUtil.adjustDeadband(driveVector, Driver.DEADBAND_VEC);
        RadiusDriveCalculator.INSTANCE
                .setQuick(Math.abs(driver.JS_TURN.getTwist()) > Driver.TWIST_DEADBAND || driveVector.getY() == 0);
        Vector2d controlVector = driver.driveCalc.compute(driveVector);
        switch (driver.dMode) {
            case POWER:
                driveTrain.setPowerLeftRight(controlVector);
                break;
            default:
                driveTrain.stop();
                break;
        }
    }

    @Override
    protected void setZero() {
        driveTrain.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
