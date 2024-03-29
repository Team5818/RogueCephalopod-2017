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

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Routine for part of a two gear auto.
 */
public class TwoGearSegment extends CommandGroup {

    private double maxPower;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private Command driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    /**
     * Creates a new segment.
     * 
     * @param dir - the direction to travel in this segment
     * @param side - the side this segment should be going towards
     * @param extra - the extra action to take (may be null)
     * @param maxPow - the maximum power to drive at
     */
    public TwoGearSegment(Direction dir, Side side, AutoExtra extra, double maxPow) {
        maxPower = maxPow;
        drive = new CommandGroup();
        whileDriving = new CommandGroup();

        final double leftRatAdd = 1.0;
        double rat = 1.6;
        // resolve the radius that we want
        // for the circle we're driving on
        double radius;
        if (side.equals(Side.RIGHT)) {
            radius = rat + .5;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / (rat + leftRatAdd);
        } else {
            radius = 1.0;
        }

        if (dir.equals(Direction.FORWARD)) {
            // setup drive portions for going forward
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(12);
                b.maxPower(maxPower);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(56);
                b.maxPower(maxPower);
                b.maxRatio(3.5);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(6);
                b.maxPower((maxPower * 2) / 3);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            // setup drive portions for going backwards
            if (side != Side.CENTER) {
                driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                    b.inches(28);
                    b.maxPower(-maxPower);
                    double rat2 = 2;
                    if (side == Side.LEFT) {
                        b.targetRatio(rat2 - .6);
                    } else {
                        b.targetRatio(1.0 / rat2);
                    }
                    b.stoppingAtEnd(false);
                });
            } else {
                driveOvershoot = new InstantCommand();
            }
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if (side == Side.CENTER) {
                    b.inches(75);
                } else {
                    b.inches(44);
                }
                b.maxPower(-maxPower);
                b.maxRatio(5);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(5);
                b.maxPower(-maxPower);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        }

        // queue drive portions
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);

        if (extra == AutoExtra.COLLECT) {
            // collection command group
            whileDriving.addSequential((new SetArmAngle(Arm.COLLECT_POSITION)));
            whileDriving.addSequential(new SetTurretAngle(Turret.TURRET_CENTER_POS));
            whileDriving.addSequential(new CollectGear(.75, 5));
        } else if (extra == AutoExtra.PLACE) {
            // placement command group
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
            whileDriving.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true, 1.0, 1.0));
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
        }

        // do both while driving for MAXIMUM EFFICIENT!
        this.addParallel(drive);
        this.addParallel(whileDriving);
    }

}
