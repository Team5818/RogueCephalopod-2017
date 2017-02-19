package org.usfirst.frc.team5818.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class MathUtil {

    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }

    public static boolean deadband(Joystick joy, double band) {
        if (Math.abs(joy.getX()) > band || Math.abs(joy.getY()) > band) {
            return true;
        }
        return false;
    }

    public static Vector2d adjustDeadband(Joystick joy, Vector2d band) {
        return new Vector2d(adjustBand(joy.getX(), band.getX()),
                adjustBand(joy.getY(), band.getY()));
    }

    public static Vector2d adjustDeadband(Vector2d joy, Vector2d band) {
        return new Vector2d(adjustBand(joy.getX(), band.getX()),
                adjustBand(joy.getY(), band.getY()));
    }

    private static double adjustBand(double jVal, double min) {
        double abs = Math.abs(jVal);
        if (abs < min) {
            return 0;
        }
        double sign = Math.signum(jVal);
        return sign * map(abs, 0.2, 1, 0, 1);
    }

    private static double map(double in, double lowIn, double highIn,
            double lowOut, double highOut) {
        double percentIn = (in - lowIn) / (highIn - lowIn);
        return percentIn * (highOut - lowOut) + lowOut;
    }
}
