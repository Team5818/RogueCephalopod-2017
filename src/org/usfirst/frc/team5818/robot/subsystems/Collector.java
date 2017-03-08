package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.constants.BotConstants;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Collector extends Subsystem {

    private CANTalon topRoller;
    private CANTalon botRoller;

    public Collector() {
        topRoller = new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER);
        botRoller = new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER);
    }

    public void setTopPower(double x) {
        topRoller.set(x * BotConstants.MAX_POWER);
    }

    public void setBotPower(double x) {
        botRoller.set(x * BotConstants.MAX_POWER);
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

    @Override
    protected void initDefaultCommand() {

    }

}
