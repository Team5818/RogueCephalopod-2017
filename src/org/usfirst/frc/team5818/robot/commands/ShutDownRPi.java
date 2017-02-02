package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;

public class ShutDownRPi extends Command{

	SerialPort rPi;
	boolean done;
	
	public ShutDownRPi(){
		rPi = Robot.runningrobot.track.getRasPi();
		done = false;
	}
	
	@Override
	protected void initialize(){
		try{
			rPi.writeString("s");
		}
		catch(Exception e){
			DriverStation.reportError("Writing Failed", false);
		}
		done = true;
	}
	
	@Override
	protected boolean isFinished() {
		return done;
	}

}
