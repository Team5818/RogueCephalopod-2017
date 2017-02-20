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
	this.addSequential(new CollectGearCurrent(.3));
	this.addSequential(new TimedCommand(.8));
    }
    
    @Override
    protected void end(){
	roll.setBotPower(0.0);
	roll.setTopPower(0.0);
    }
      

}
