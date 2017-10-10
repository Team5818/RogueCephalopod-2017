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
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Routine to collect a gear.
 */
public class CollectGear extends CommandGroup {

    private final double power;
    Collector collect;

    public CollectGear() {
        this(.7, 1000);
    }

    public CollectGear(double pow, double limitTimeout) {
        collect = Robot.runningRobot.collect;
        power = pow;
        this.addSequential(new LimitCollect(pow, limitTimeout));
        this.addSequential(new TimedCommand(.10));
        this.addSequential(new SetCollectorPower(false, 0.2, 0.2));
    }

    @Override
    protected void initialize() {
        collect.setBotPower(power);
        collect.setTopPower(power);
    }

    @Override
    protected void end() {
        collect.setBotPower(0.0);
        collect.setTopPower(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
