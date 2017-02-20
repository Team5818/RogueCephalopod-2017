package org.usfirst.frc.team5818.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.constants.BotConstants;

public class CollectorRollers extends Subsystem {
    
    private CANTalon topRoller;
    private CANTalon botRoller;
    private Ultrasonic sanic;
    
    public CollectorRollers() {
        topRoller = new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER);
        botRoller = new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER);
        sanic = new Ultrasonic(2,3);
        enableSanic();
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
    
    public Ultrasonic getSanic(){
        return sanic;
    }
    
    public void enableSanic(){
        sanic.setAutomaticMode(true);
    }
    
    
    public double readSanic(){
        return sanic.getRangeInches();
    }

    @Override
    protected void initDefaultCommand() {
        
    }

}
