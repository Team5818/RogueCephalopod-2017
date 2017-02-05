package org.usfirst.frc.team5818.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Joystick Numbers
	public static final int D_JOY_1 = 0;
	public static final int D_JOY_2 = 1;
	public static final int CD_JOY_1 = 2;
	public static final int CD_JOY_2 = 3;
	
	// Talon numbers; -ENC ones have encoders
	public static final int R_TALON = 2;
	public static final int R_TALON_ENC = 1;
	public static final int L_TALON = 3;
	public static final int L_TALON_ENC = 4;
	public static final int TURR_MOTOR = 5;
	public static final int ARM_TALON_L = 6;
	public static final int ARM_TALON_R = 7;
}
