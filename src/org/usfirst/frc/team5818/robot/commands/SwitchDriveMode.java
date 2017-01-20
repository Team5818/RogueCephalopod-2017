package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.controllers.Driver;
import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command{
	
	Driver driver;
	Driver.DriveMode targetMode;
	private boolean isDone = false;
	
	public SwitchDriveMode(Driver.DriveMode mode){
		driver = Robot.runningrobot.driver;
		targetMode = mode;
	}
	
	@Override
	protected void initialize(){
		driver.dMode = targetMode;
		isDone = true;
	}
	
	@Override
	protected boolean isFinished() {
		return isDone;
	}

}
