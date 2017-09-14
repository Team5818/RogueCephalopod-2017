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

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives, magically, with motion magic.
 */
public class MagicDrive extends Command {

    private DriveTrain dt;
    private double distance;

    public MagicDrive(double dist) {
        distance = dist;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(4);
    }

    @Override
    public void initialize() {
        dt.getLeftSide().positionControl();
        dt.getRightSide().positionControl();
        dt.getLeftSide().driveDistanceNoReset(dt.getLeftSide().getSidePosition() + distance, 300, 300);
        dt.getRightSide().driveDistanceNoReset(dt.getRightSide().getSidePosition() + distance, 300, 300);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(dt.getAvgSidePosition() - distance) < .5 && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2 || isTimedOut();
    }

    @Override
    protected void end() {
        dt.stop();
    }

}
