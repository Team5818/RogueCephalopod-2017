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
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Punches/un-punches the turret.
 */
public class SetPunchTurret extends Command {

    private Turret turr;
    private boolean on;

    public SetPunchTurret(boolean b, double timeout) {
        turr = Robot.runningRobot.turret;
        requires(turr.deployer);
        on = b;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        turr.punch(on);
    }

    @Override
    protected void execute() {
        turr.punch(on);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
