package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class AimTurret extends Command{

	private Turret turr;
	private VisionTracker track; 
	private double degreesOff;
	public static final double DEGREES_TOLERANCE = 5;
	
	public void AimTurret(double ang){
		turr = Robot.runningrobot.turret;
		track = Robot.runningrobot.track;
	}
	
	public void initialize(){
		if(!SwitchFeed.isGear()){
			SwitchFeed sf = new SwitchFeed();
			sf.start();
		}
	}
	
	@Override
	protected void execute(){
		degreesOff = track.getCurrentX() * (BotConstants.CAMERA_FOV/240.0);
		turr.setAng(degreesOff - turr.getAng());
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs((degreesOff - track.getCurrentX() * (BotConstants.CAMERA_FOV/240.0))) < DEGREES_TOLERANCE;
	}

	
	
}
