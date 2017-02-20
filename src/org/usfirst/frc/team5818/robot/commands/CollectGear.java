package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGear extends CommandGroup{

    CollectorRollers roll; 
    
    public CollectGear(){
	roll = Robot.runningRobot.roll;
	this.addSequential(new TimedCommand(.3));
	this.addSequential(new CollectGearCurrent(.7));
	this.addSequential(new TimedCommand(.3));
    }
    
    @Override
    protected void initialize(){
	roll.setBotPower(.7);
	roll.setTopPower(.7);
    }
    
    @Override
    protected void end(){
	roll.setBotPower(0.0);
	roll.setTopPower(0.0);
    }
      

}
