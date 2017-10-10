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
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.MagicSpin;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * A two-gear auto that is for the gear being placed on the right side.
 */
public class TwoGearAutoRight extends CommandGroup {

    private TwoGearSegment moveForward;
    private TwoGearSegment moveToGear;
    private TwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public TwoGearAutoRight() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.9);

        gearMode = new GearMode();
        moveToGear = new TwoGearSegment(Direction.FORWARD, Side.RIGHT, AutoExtra.COLLECT, -.75);
        tapeMode2 = new TapeMode();
        moveToPeg = new TwoGearSegment(Direction.BACKWARD, Side.RIGHT, AutoExtra.PLACE, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new TimedCommand(.2));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        // Drive Down the Field
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new ShiftGears(Gear.HIGH));
        this.addSequential(new MagicDrive(40.0, 400));
        this.addSequential(new MagicSpin(Math.PI/2, 300));
        this.addSequential(new MagicDrive(120.0, 500));
        this.addSequential(new MagicSpin(Math.PI, 300));
        this.addSequential(new MagicDrive(320.0, 500));
    }

}
