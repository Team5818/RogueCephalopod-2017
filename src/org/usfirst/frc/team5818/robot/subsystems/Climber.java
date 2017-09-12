package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Simple subsystem for collector. Has a top and bottom roller, gear gets sucked
 * in between rollers
 */
public class Climber extends Subsystem {

    private CANTalon left1;
    private CANTalon right1;

    public Climber() {
        left1 = new CANTalon(RobotMap.LEFT_CLIMB_TALON_1);
        right1 = new CANTalon(RobotMap.RIGHT_CLIMB_TALON_1);
        right1.setInverted(true);
    }

    public void setPower(double pow) {
        left1.set(pow);
        right1.set(pow);
    }

    public double getLeftCurrent() {
        return left1.getOutputCurrent();
    }

    public double getRightCurrent() {
        return right1.getOutputCurrent();
    }

    @Override
    protected void initDefaultCommand() {
    }

}
