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
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Routine to move the arm to the collect position and get a gear.
 */
public class MoveArmCollect extends CommandGroup {

    private CommandGroup moveArm;
    private CommandGroup raiseGroup;
    
    public MoveArmCollect() {
        moveArm = new CommandGroup();
        moveArm.addParallel(new CollectGear(.75, 1000));
        moveArm.addParallel(new SetArmAngle(Arm.COLLECT_POSITION));
        raiseGroup = new CommandGroup();
        raiseGroup.addParallel(new SetArmAngle(Arm.MID_POSITION));
        raiseGroup.addParallel(new BlinkThreeTimes());
        this.addSequential(moveArm);
        this.addSequential(raiseGroup);
    }

    @Override
    protected void end() {
        Robot.runningRobot.collect.setBotPower(0.0);
        Robot.runningRobot.collect.setTopPower(0.0);
        Robot.runningRobot.vision.setLightsOn(false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
