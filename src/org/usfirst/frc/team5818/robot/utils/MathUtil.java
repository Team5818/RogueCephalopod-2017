package org.usfirst.frc.team5818.robot.utils;

public class MathUtil{

    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }
    //TODO: More math utilities
}
