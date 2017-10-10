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
package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command that first discovers where to place with {@link DiscoverPlacePosition
 * DPP}, then places with {@link PlaceGearLimit}.
 */
public class PlaceWithLimit extends CommandGroup {

	public PlaceWithLimit() {
		this.setInterruptible(false);
		addSequential(new DiscoverPlacePosition());
		// this.addSequential(new ConditionalCommand(new PlaceGearLimit(), new
		// QuickPlace()) {
		//
		// @Override
		// protected boolean condition() {
		// return Math.abs(Robot.runningRobot.turret.getAngle()) < 45;
		// }
		// });
		addSequential(new PlaceGearLimit());
	}

}
