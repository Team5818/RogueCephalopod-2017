package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.controllers.Driver;
import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchControlMode extends Command{
	
	Driver driver;
	Driver.ControlMode targetMode;
	private boolean isDone = false;
	
	public SwitchControlMode(Driver.ControlMode mode){
		driver = Robot.runningrobot.driver;
		targetMode = mode;
	}
	
	@Override
	protected void initialize(){
		if(!driver.cMode.equals(Driver.ControlMode.RADIUS) && targetMode.equals(Driver.ControlMode.QUICK_RADIUS)){
			isDone = true;
			return;
		}
		driver.cMode = targetMode;
		isDone = true;
	}
	
	@Override
	protected boolean isFinished() {
		return isDone;
	}

}