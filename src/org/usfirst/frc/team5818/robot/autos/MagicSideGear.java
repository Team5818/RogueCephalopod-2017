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

import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.MagicSpin;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Every single side gear auto in one command
 */
public class MagicSideGear extends CommandGroup{
    
    private Position position;
    private double firstDistance;
    private double turnAngle;
    private double secondDistance;
    
    public enum Position{
        BLUE_BOILER, BLUE_OPPOSITE, RED_BOILER, RED_OPPOSITE
    }
    
    public MagicSideGear(Position pos) {
        position = pos;
        switch(position) {
            case BLUE_BOILER:
                firstDistance = -69.6;
                turnAngle = Math.toRadians(57);
                secondDistance = -68;
                break;
            case BLUE_OPPOSITE:
                firstDistance = -70.4;
                turnAngle = Math.toRadians(-60);
                secondDistance = -67;
                break;
            case RED_BOILER:
                firstDistance = -69.6;
                turnAngle = Math.toRadians(-57);
                secondDistance = -68;
                break;
            case RED_OPPOSITE:
                firstDistance = -70.4;
                turnAngle = Math.toRadians(60);
                secondDistance = -67;
                break;
        }
        
        this.addSequential(new MagicDrive(firstDistance, 400));
        this.addSequential(new MagicSpin(turnAngle));
        this.addSequential(new TapeMode());
        //this.addSequential(new MagicSpinToVision());
        this.addSequential(new ShiftGears(Gear.LOW));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(Math.abs(secondDistance) - 5);
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
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new ShiftGears(Gear.HIGH));
        this.addSequential(new MagicDrive(-secondDistance));
        this.addSequential(new MagicSpin(Math.PI));
        this.addSequential(new MagicDrive(320.0));
    }
    
}
