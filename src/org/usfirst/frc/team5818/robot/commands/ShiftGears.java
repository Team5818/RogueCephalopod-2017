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
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Changes gear on the drive train.
 */
public class ShiftGears extends Command {

    private DriveTrain train;
    private Gear gear;
    private static double SHIFT_TIME = .5;

    public ShiftGears(Gear g) {
        this(g, SHIFT_TIME);
    }

    public ShiftGears(Gear g, double timeout) {
        train = Robot.runningRobot.driveTrain;
        gear = g;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        train.shiftGears(gear);
        train.setMaxPower(.5);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        train.setMaxPower(1.0);
    }

}
