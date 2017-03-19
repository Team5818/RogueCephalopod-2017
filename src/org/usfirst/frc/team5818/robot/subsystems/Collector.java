package org.usfirst.frc.team5818.robot.subsystems;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Collector extends Subsystem {

    private static final double MAX_POWER = Constant.maxPower();

    private CANTalon topRoller;
    private CANTalon botRoller;
    private DigitalInput limitSwitch;

    public Collector() {
        topRoller = new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER);
        botRoller = new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER);
        limitSwitch = new DigitalInput(RobotMap.COLLECTOR_LIMIT_SWITCH);
    }

    public void setTopPower(double x) {
        topRoller.set(x * MAX_POWER);
    }

    public void setBotPower(double x) {
        botRoller.set(x * MAX_POWER);
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

    public boolean isLimitTriggered() {
        return !limitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}
