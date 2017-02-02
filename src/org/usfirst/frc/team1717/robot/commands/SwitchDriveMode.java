package org.usfirst.frc.team1717.robot.commands;

import org.usfirst.frc.team1717.controllers.Driver;
import org.usfirst.frc.team1717.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command{
	
	Driver driver;
	private boolean isDone = false;
	
	public SwitchDriveMode(){
		driver = Robot.runningrobot.driver;
	}
	
	@Override
	protected void initialize(){
		if(driver.dMode.equals(Driver.DriveMode.POWER)){
			driver.dMode = Driver.DriveMode.VELOCITY;
		}
		else if(driver.dMode.equals(Driver.DriveMode.VELOCITY)){
			driver.dMode = Driver.DriveMode.POWER;
		}
		isDone = true;
	}
	
	@Override
	protected boolean isFinished() {
		return isDone;
	}

}
