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
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Switches the drive mode to a different calculator.
 */
public class SwitchDriveMode extends Command {

    private Driver driver;
    private DriveCalculator driveCalc;

    public SwitchDriveMode(DriveCalculator calc) {
        driver = Robot.runningRobot.driver;
        driveCalc = calc;
    }

    @Override
    protected void initialize() {
        driver.driveCalc = driveCalc;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
