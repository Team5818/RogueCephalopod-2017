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

import edu.wpi.first.wpilibj.command.Command;

/**
 * Starts the {@link ClimbMode} command after stopping the turret zero command.
 */
public class StartClimbControlCommand extends Command {

    private enum State {
        CANCEL_ZERO, START_CCC, FINISHED;
    }

    private State state = State.CANCEL_ZERO;

    @Override
    protected void execute() {
        switch (state) {
            case CANCEL_ZERO:
                Robot.runningRobot.turretSafetyChecks = false;
                Robot.runningRobot.turretZero.cancel();
                state = State.START_CCC;
                break;
            case START_CCC:
            default:
                new ClimbMode().start();
                state = State.FINISHED;
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return state == State.FINISHED;
    }

}
