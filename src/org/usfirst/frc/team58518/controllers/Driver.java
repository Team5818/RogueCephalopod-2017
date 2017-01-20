package org.usfirst.frc.team58518.controllers;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import utils.ArcadeDriveCalculator;
import utils.Vector2d;
import utils.Vectors;

public class Driver {
	Joystick JS_FW_BACK;
	Joystick JS_TURN;
	DriveTrain train;
	double deadband = .2;
	
	
	public enum DriveMode{
		POWER, VELOCITY, STOPPED
	}
	
	public DriveMode mode;
	
	public Driver() {
		JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
		JS_TURN = new Joystick(BotConstants.JS_TURN);
		train = Robot.runningrobot.driveTrain;
		mode = DriveMode.POWER;
	}
	
	public void teleopPeriodic(){
		drive();
	}
	
	public void drive(){
		Vector2d driveVector = Vectors.fromJoystick(JS_FW_BACK, JS_TURN, true);
		if(mode == DriveMode.POWER){
		    Vector2d arcadeVector = ArcadeDriveCalculator.INSTANCE.compute(driveVector);
		    train.setPowerLeftRight(arcadeVector);
		}
		else if(mode == DriveMode.VELOCITY){
			Vector2d arcadeVector = ArcadeDriveCalculator.INSTANCE.compute(driveVector);
			Vector2d velVector = new Vector2d(arcadeVector.getX()*BotConstants.ROBOT_MAX_VELOCITY,
					arcadeVector.getY()*BotConstants.ROBOT_MAX_VELOCITY);
			train.setVelocityLeftRight(velVector);
		}
		else{
			train.brake();
		}
	}	
	
}
