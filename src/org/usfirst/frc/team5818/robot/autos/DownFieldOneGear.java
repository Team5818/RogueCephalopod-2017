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
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * One gear for a specific side, that then drives down field.
 */
public class DownFieldOneGear extends CommandGroup {

    private TwoGearSegment moveForward;
    private TapeMode tapeMode;

    public DownFieldOneGear(Side side) {
        double angMult;
        double sideDist;
        /// FOR BLUE SIDE
        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
            if (side == Side.LEFT) {
                angMult = 1;
                sideDist = 80;
            } else {
                angMult = -1;
                sideDist = 120;
            }
        } else {
            if (side == Side.RIGHT) {
                angMult = 1;
                sideDist = 80;
            } else {
                angMult = -1;
                sideDist = 120;
            }
        }

        setInterruptible(false);
        tapeMode = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode);
        this.addSequential(moveForward);
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());

        this.addSequential(new DriveTrajectory(40, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(angMult * Math.toRadians(80), true, true));
        this.addSequential(new DriveTrajectory(sideDist, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(angMult * Math.PI, true, true));
        this.addSequential(new DriveTrajectory(370, angMult * Math.PI, 0.0, 0.0, Direction.FORWARD, true));

    }

}
