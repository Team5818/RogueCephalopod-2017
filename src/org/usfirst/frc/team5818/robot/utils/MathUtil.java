package org.usfirst.frc.team5818.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class MathUtil{

    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }
    
    public static boolean deadband(Joystick joy, double band){
    	if(Math.abs(joy.getX()) > band || Math.abs(joy.getY()) > band){
    		return true;
    	}
    	return false;
    }
}
