package org.usfirst.frc.team5818.robot;

import java.util.Arrays;

import com.ctre.CANTalon;

/**
 * A mapping from indexes to the actual talons on the robot.
 */
public enum TestingTalon {
    DRIVE_ONE(RobotMap.DRIVE_TALONS[0]), DRIVE_TWO(RobotMap.DRIVE_TALONS[1]), DRIVE_THREE(RobotMap.DRIVE_TALONS[2]),
    DRIVE_FOUR(RobotMap.DRIVE_TALONS[3]), DRIVE_FIVE(RobotMap.DRIVE_TALONS[4]), DRIVE_SIX(RobotMap.DRIVE_TALONS[5]),
    TURRET(RobotMap.TURR_MOTOR), LEFT_ARM(RobotMap.ARM_TALON_L), RIGHT_ARM(RobotMap.ARM_TALON_R),
    TOP_ROLLER(RobotMap.TOP_COLLECTOR_ROLLER), BOT_ROLLER(RobotMap.BOT_COLLECTOR_ROLLER),
    CLIMB_ONE(RobotMap.CLIMB_TALONS[0]), CLIMB_TWO(RobotMap.CLIMB_TALONS[1]), CLIMB_THREE(RobotMap.CLIMB_TALONS[2]),
    CLIMB_FOUR(RobotMap.CLIMB_TALONS[3]);

    public static final TestingTalon[] DRIVE = Arrays.copyOfRange(values(), 0, 6);
    public static final TestingTalon[] CLIMB = Arrays.copyOfRange(values(), 11, 15);

    public final CANTalon talon;

    TestingTalon(int talonId) {
        this.talon = new CANTalon(talonId);
    }

    static {
        // Invert some talons
        RIGHT_ARM.talon.setInverted(true);
    }
}
