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
package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

/**
 * The options used by {@link DriveAtRatio}. Uses one of the implementations to
 * properly configure each option.
 */
public interface DriveAtRatioOptions {

	Camera getCamera();

	Spin getRotation();

	double getInches();

	double getMaxPower();

	double getMinPower();

	double getVisionOffset();

	double getAccel();

	double getMaxRatio();

	double getTargetRatio();

	boolean isStoppingAtEnd();

	default boolean isUsingVision() {
		return getCamera() == Camera.CAM_TAPE || getCamera() == Camera.CAM_GEARS;
	}

	default boolean isSpinning() {
		return getRotation() != null;
	}

	default boolean isProfiling() {
		return getAccel() != 0;
	}

}
