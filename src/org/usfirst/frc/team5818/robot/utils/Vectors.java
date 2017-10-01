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

import edu.wpi.first.wpilibj.Joystick;

/**
 * Helper class for vectors.
 */
public final class Vectors {

    /**
     * Creates a vector from the joystick axis values.
     * 
     * @param stick
     *            - The joystick to pull values from
     * @param invertedThrottle
     *            - Whether to invert throttle control similar to plane control.
     * 
     * @return A new vector of the values
     */
    public static Vector2d fromJoystick(Joystick stick, boolean invertedThrottle) {
        int sign = 1;
        if (invertedThrottle)
            sign *= -1;
        return new Vector2d(stick.getX(), sign * -stick.getY());
    }

    /**
     * Creates a vector from the joystick axis values.
     * 
     * @param stick
     *            - The joystick to pull throttle values from
     * @param stick2
     *            - The joystick to pull turn values from
     * @param invertedThrottle
     *            - Whether to invert throttle control similar to plane control.
     * 
     * @return A new vector of the values
     */
    public static Vector2d fromJoystick(Joystick stick, Joystick stick2, boolean invertedThrottle) {
        int sign = 1;
        if (invertedThrottle)
            sign *= -1;
        return new Vector2d(stick2.getX(), sign * -stick.getY());
    }

    /**
     * Creates a vector from the joystick axis values.
     * 
     * @param stick
     *            - The joystick to pull left motor throttle values from
     * @param stick2
     *            - The joystick to pull right motor throttle values from
     * @param invertedThrottle
     *            - Whether to invert throttle control similar to plane control.
     * 
     * @return A new vector of the values
     */
    public static Vector2d fromJoystickTank(Joystick stick, Joystick stick2, boolean invertedThrottle) {
        int sign = 1;
        if (invertedThrottle)
            sign *= -1;
        return new Vector2d(sign * -stick.getY(), sign * -stick2.getY());
    }

    /**
     * Cannot be constructed.
     */
    private Vectors() {
    }

}
