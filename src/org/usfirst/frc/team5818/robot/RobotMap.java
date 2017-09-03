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

    public static final int[] DRIVE_TALONS = { 1, 2, 3, 4, 5, 6 };
    // Talon numbers; -ENC ones have encoders
    public static final int R_TALON = DRIVE_TALONS[0];
    public static final int R_TALON_ENC = DRIVE_TALONS[1];
    public static final int R_TALON_2 = DRIVE_TALONS[2];
    public static final int L_TALON = DRIVE_TALONS[4];
    public static final int L_TALON_ENC = DRIVE_TALONS[3];
    public static final int L_TALON_2 = DRIVE_TALONS[5];
    public static final int TURR_MOTOR = 7;
    public static final int ARM_TALON_L = 8;
    public static final int ARM_TALON_R = 9;
    public static final int TOP_COLLECTOR_ROLLER = 10;
    public static final int BOT_COLLECTOR_ROLLER = 11;

    public static final int[] CLIMB_TALONS = { 12, 13, 14, 16 };
    public static final int LEFT_CLIMB_TALON_1 = CLIMB_TALONS[2];
    public static final int RIGHT_CLIMB_TALON_1 = CLIMB_TALONS[3];
    public static final int LEFT_CLIMB_TALON_2 = CLIMB_TALONS[0];
    public static final int RIGHT_CLIMB_TALON_2 = CLIMB_TALONS[1];

    //Pneumatics
    public static final int TURRET_EXTENDER_SOLENOID = 1;
    public static final int TURRET_PUNCHER_SOLENOID = 2;
    public static final int SHIFTER_SOLENOID = 5;
    public static final int LED_SOLENOID = 6;

    //Limit Switches
    public static final int TURRET_LIMIT_SWITCH = 5;
    public static final int COLLECTOR_LIMIT_SWITCH = 4;
    
    //Potentiometers
    public static final int TURRET_POT = 0;

}
