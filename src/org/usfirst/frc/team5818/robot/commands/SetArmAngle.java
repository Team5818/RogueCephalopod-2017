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
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the arm to an angle.
 */
public class SetArmAngle extends Command {

    public static final double TOLERANCE = 50;

    private Arm arm;
    private double targetAng;

    public SetArmAngle(double angle) {
        setTimeout(1.5);
        arm = Robot.runningRobot.arm;
        targetAng = angle;
        requires(arm);
    }

    @Override
    public void initialize() {
        // reset turret if target is too high
        if (targetAng >= Arm.TURRET_RESET_POSITION) {
            Robot.runningRobot.runTurretOverrides();
        }
        arm.setBrakeMode(false);
        arm.setAngle(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(arm.getPositionRaw() - targetAng) < 20 && Math.abs(arm.getVelocity()) < 2) ||  isTimedOut();
    }

    @Override
    public void end() {
        // arm.setManual();
        // arm.setBrakeMode(true);
        // arm.setPower(0.0);
    }

}
