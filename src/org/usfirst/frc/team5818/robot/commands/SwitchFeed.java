package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import edu.wpi.first.wpilibj.command.Command;

public class SwitchFeed extends Command{

	private CameraController cont;
	private boolean done;
<<<<<<< HEAD
	private static boolean gear;
	
	public static boolean isGear(){
		return gear;
	}
=======
>>>>>>> origin/feature/camera-controller
	
	public SwitchFeed(){
		cont = Robot.runningrobot.camCont;
		done = false;
	}
	
	@Override
	protected void initialize(){
<<<<<<< HEAD
		try{
			rPi.writeString("w");
			gear = !gear;
		}
		catch(Exception e){
			DriverStation.reportError("Writing Failed", false);
		}
		done = true;
=======
	    cont.switchFeed();
	    done = true;
>>>>>>> origin/feature/camera-controller
	}
	
	@Override
	protected boolean isFinished() {
		return done;
	}

}