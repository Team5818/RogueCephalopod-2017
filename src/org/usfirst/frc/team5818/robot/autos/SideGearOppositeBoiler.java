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
package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.FindTarget;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Auto for placing a gear from the collection side. Uses a side parameter to
 * figure out where to go on each alliance side.
 */
public class SideGearOppositeBoiler extends CommandGroup {

    int angleMult;

    public SideGearOppositeBoiler(Side turnSide) {
        Spin s;
        if (turnSide == Side.LEFT) {
            s = Spin.COUNTERCW;
            angleMult = -1;

        } else {
            s = Spin.CLOCKWISE;
            angleMult = 1;
        }
        addSequential(new TapeMode());
        addSequential(new DriveTrajectory(72, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new FindTarget(s, 35));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(74);
            b.maxPower(.7);
            b.maxRatio(3.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(5);
            b.maxPower(.7);
            b.targetRatio(1);
            b.stoppingAtEnd(true);
        }));
        this.addSequential(new TimedCommand(.5));
        addSequential(new PlaceWithLimit());
        addSequential(new DriveTrajectory(28, angleMult * Math.toRadians(60.0), 0.0, 0.0, Direction.FORWARD, true));
        addSequential(new SpinWithProfile(angleMult * Math.PI, true, false));
        addSequential(new DriveTrajectory(320, .7 * (angleMult * Math.PI), 0.0, 0.0, Direction.FORWARD, true));
    }
}
