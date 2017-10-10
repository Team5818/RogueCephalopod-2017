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

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Stream;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Super-command for all joystick default commands.
 */
public abstract class ControlCommand extends Command {

    protected static Joystick js(Function<Driver, Joystick> fieldGet) {
        return fieldGet.apply(Robot.runningRobot.driver);
    }

    private final BooleanSupplier anyOutOfBand;
    protected final Driver driver = Robot.runningRobot.driver;

    protected ControlCommand(Joystick... joysticks) {
        anyOutOfBand = Stream.of(joysticks).map(j -> {
            BooleanSupplier b = () -> MathUtil.outOfDeadband(j, Driver.JOYSTICK_DEADBAND);
            return b;
        }).reduce((a, b) -> {
            BooleanSupplier r = () -> a.getAsBoolean() || b.getAsBoolean();
            return r;
        }).orElseThrow(() -> new IllegalArgumentException("Please provide at least one stick"));
    }

    protected abstract void setPower();

    protected abstract void setZero();

    @Override
    protected final void execute() {
        if (anyOutOfBand.getAsBoolean()) {
            setPower();
        } else {
            setZero();
        }
    }

    @Override
    protected final void end() {
        setZero();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
