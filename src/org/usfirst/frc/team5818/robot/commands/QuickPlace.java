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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Test for quick placement.
 */
public class QuickPlace extends CommandGroup {

    public QuickPlace() {
        this.addSequential(new SetExtendTurret(true));
        this.addSequential(new TimedCommand(.2));
        this.addSequential(new SetPunchTurret(true, .01));
        this.addSequential(new TimedCommand(.1));
        this.addSequential(new SetExtendTurret(false));
        this.addSequential(new TimedCommand(.3));
        this.addSequential(new SetPunchTurret(false, .01));
    }

}
