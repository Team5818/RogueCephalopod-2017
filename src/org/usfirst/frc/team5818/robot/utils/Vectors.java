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
    public static Vector2d fromJoystick(Joystick stick,
            boolean invertedThrottle) {
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
    public static Vector2d fromJoystick(Joystick stick, Joystick stick2,
            boolean invertedThrottle) {
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
    public static Vector2d fromJoystickTank(Joystick stick, Joystick stick2,
            boolean invertedThrottle) {
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
