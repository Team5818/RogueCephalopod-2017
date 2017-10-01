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
package org.usfirst.frc.team5818.robot.utils;

/**
 * Calculators map raw power values (from joysticks or provided in auto) to
 * talon power.
 */
public interface DriveCalculator {

    /**
     * Converts raw power to talon power.
     * 
     * @param leftAndRight
     *            - raw power, as (x-axis, y-axis)
     * @return left and right talon power, as (left, right)
     */
    Vector2d compute(Vector2d leftAndRight);

}
