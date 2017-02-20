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
    public static final int L_TALON = 1;
    public static final int L_TALON_ENC = 2;
    public static final int L_TALON_2 = 3;
    public static final int R_TALON = 4;
    public static final int R_TALON_ENC = 5;
    public static final int R_TALON_2 = 6;
    public static final int TURR_MOTOR = 7;
    public static final int ARM_TALON_L = 8;
    public static final int ARM_TALON_R = 9;
    public static final int TOP_COLLECTOR_ROLLER = 10;
    public static final int BOT_COLLECTOR_ROLLER = 11;
    public static final int LEFT_CLIMB_TALON_1 = 15;
    public static final int RIGHT_CLIMB_TALON_1 = 14;
    public static final int LEFT_CLIMB_TALON_2 = 12;
    public static final int RIGHT_CLIMB_TALON_2 = 13;
	
}
