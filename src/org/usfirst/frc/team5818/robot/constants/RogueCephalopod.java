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
package org.usfirst.frc.team5818.robot.constants;

/**
 * Implementation of constants for the real robot.
 */
public final class RogueCephalopod extends Constants {

    RogueCephalopod() {
    }

    @Override
    public double wheelToWheelWidth() {
        return 27.25;
    }

    @Override
    public double turretScale() {
        return -90.0 / 100.0;
    }

    @Override
    public double turretCenter() {
        return 1921;
    }

    @Override
    public double encoderScale() {
        return 4.0 * (72.0 / 2180.0);
    }

    @Override
    public double maxVelocityIPS() {
        return 72.0;
    }

    @Override
    public double maxAccelIPS2() {
        return 150;
    }
}
