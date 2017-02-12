package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import java.lang.Math;

import edu.wpi.first.wpilibj.command.Command;

public class AimTurret extends Command{

	private Turret turr;
	private VisionTracker track; 
	private CameraController cont;
	private double degreesOff;
	public static final double DEGREES_TOLERANCE = 2;
	
	public static final double distFromTarget = 36; //Estimation in inches
	public static final double cameraOffset = 6;//Estimation in inches
	
	/*
	 * Finds field of vision taking into account camera offset.
	 * Assumes distance from the target and uses tangents to find angle
	 * Is the assumption worth it? Maybe we want a way to find distance from target?
	 * Or just assume camera is at center of turret?
	 */
	public static final double realFOV = 2 * Math.toDegrees(Math.atan2(
	        Math.tan(BotConstants.CAMERA_FOV / 2) * distFromTarget,
	        distFromTarget + cameraOffset));
	
	public AimTurret(){
		turr = Robot.runningrobot.turret;
		track = Robot.runningrobot.track;
		cont = Robot.runningrobot.camCont;
	}
	
	public void initialize(){
	    cont.tapeMode();
	}
	
	@Override
	protected void execute(){
		degreesOff = track.getCurrentAngle();
		turr.setAng(degreesOff - turr.getAng());
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs(degreesOff - track.getCurrentAngle()) < DEGREES_TOLERANCE;
	}

	
	
}
