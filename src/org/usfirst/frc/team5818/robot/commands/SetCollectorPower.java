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

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the collector power to given parameters, with timeout.
 */
public class SetCollectorPower extends Command {

    private final double power;
    private final Collector collect = Robot.runningRobot.collect;

    public SetCollectorPower(boolean ejectForwards, double pow, double timeout) {
        requires(collect);
        setTimeout(timeout);
        power = ejectForwards ? pow : -pow;
    }

    public SetCollectorPower(boolean ejectForwards) {
        this(ejectForwards, 0.7, 1);
    }

    @Override
    protected void initialize() {
        collect.setTopPower(power);
        collect.setBotPower(power);
    }

    @Override
    protected void end() {
        collect.setBotPower(0.0);
        collect.setTopPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
