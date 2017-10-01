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
package org.usfirst.frc.team5818.robot.commands.debug;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A breakpoint command is used to debug command-based routines.
 * 
 * <p>
 * It logs to the dashboard the last-hit breakpoint, and allows you to stop inside of it to examine state.
 * </p>
 */
public class BreakpointCommand extends Command {

    private final String name;

    public BreakpointCommand(String name) {
        setTimeout(1);
        this.name = name;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private <T> T getGroupAs() {
        return (T) getGroup();
    }

    @Override
    protected void initialize() {
        SmartDashboard.putString("Message", name);
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected void end() {
        super.end();
    }

    protected void interrupted() {
        super.interrupted();
    };

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
