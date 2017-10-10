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
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Control command for the arm.
 */
public class ArmControlCommand extends ControlCommand {

    private final Arm arm = Robot.runningRobot.arm;
    private Joystick joystick;

    public ArmControlCommand(Joystick joy) {
        super(joy);
        joystick = joy;
        requires(arm);
    }

    @Override
    protected void initialize() {
        arm.setBrakeMode(true);
        arm.setManual();
    }

    @Override
    protected void setPower() {
        arm.setPower(MathUtil.adjustDeadband(joystick, Driver.DEADBAND_VEC).getY());
    }

    @Override
    protected void setZero() {
        arm.setPower(0);
    }

}
