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
import org.usfirst.frc.team5818.robot.subsystems.Climber;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Control command for climbing.
 */
public class ClimbControlCommand extends ControlCommand {

    private final Climber climb = Robot.runningRobot.climb;
    private final Turret turret = Robot.runningRobot.turret;
    private final Joystick js;

    public ClimbControlCommand(Joystick joystick) {
        super(joystick);
        setInterruptible(false);
        js = joystick;
        requires(climb);
        requires(turret.rotator);
        requires(turret.deployer);
    }

    @Override
    protected void setPower() {
        climb.setPower(Math.abs(MathUtil.adjustDeadband(js, Driver.DEADBAND_VEC).getY()));
        turret.setPower(0.0);
    }

    @Override
    protected void setZero() {
        climb.setPower(0.0);
        turret.setPower(0.0);
    }

}
