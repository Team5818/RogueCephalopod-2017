package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Simple subsystem for collector. Has a top and bottom roller, gear gets sucked
 * in between rollers
 */
public class Collector extends Subsystem {

    private CANTalon topRoller;
    private CANTalon botRoller;
    private DigitalInput limitSwitch;

    public Collector() {
        topRoller = new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER);
        botRoller = new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER);
        limitSwitch = new DigitalInput(RobotMap.COLLECTOR_LIMIT_SWITCH);
    }

    public void setTopPower(double x) {
        topRoller.set(x);
    }

    public void setBotPower(double x) {
        botRoller.set(x);
    }

    public void stop() {
        topRoller.set(0);
        botRoller.set(0);
    }

    public double getTopCurrent() {
        return topRoller.getOutputCurrent();
    }

    public double getBotCurrent() {
        return botRoller.getOutputCurrent();
    }

    /* Limit switch indicates when gear is collected */
    public boolean isLimitTriggered() {
        return !limitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}
