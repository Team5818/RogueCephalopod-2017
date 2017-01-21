package org.usfirst.frc.team1717.controllers;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.constants.BotConstants;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import utils.ArcadeDriveCalculator;
import utils.DriveCalculator;
import utils.RadiusDriveCalculator;
import utils.TankDriveCalculator;
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
	
	public enum ControlMode{
		ARCADE, TANK, RADIUS, QUICK_RADIUS
	}
	
	public DriveMode dMode;
	public ControlMode cMode;
	private DriveCalculator driveCalc;
	
	public Driver() {
		JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
		JS_TURN = new Joystick(BotConstants.JS_TURN);
		train = Robot.runningrobot.driveTrain;
		dMode = DriveMode.POWER;
		cMode = ControlMode.ARCADE;
		driveCalc = ArcadeDriveCalculator.INSTANCE;
	}
	
	public void teleopPeriodic(){
		handleCalc();
		drive();
	}
	
	public void drive(){
		Vector2d driveVector = Vectors.fromJoystick(JS_FW_BACK, JS_TURN, true);
		Vector2d controlVector = driveCalc.compute(driveVector);
        switch(dMode){
            case POWER:
            	train.setPowerLeftRight(controlVector);
            	break;
            case VELOCITY:
            	train.setVelocityLeftRight(controlVector.scale(BotConstants.ROBOT_MAX_VELOCITY));
            	break;
            default:
            	train.brake();
            	break;
        }
	}
	
	public void handleCalc(){
		switch(cMode){
		    case ARCADE:
		    	driveCalc = ArcadeDriveCalculator.INSTANCE;
		    	break;
		    case TANK:
		    	driveCalc = TankDriveCalculator.INSTANCE;
		    	break;
		    case RADIUS:
		    	driveCalc = RadiusDriveCalculator.INSTANCE;
		    	((RadiusDriveCalculator) driveCalc).setQuick(false);
		    	break;
		    case QUICK_RADIUS:
		    	driveCalc = RadiusDriveCalculator.INSTANCE;
		    	((RadiusDriveCalculator) driveCalc).setQuick(true);
		    default:
		    	driveCalc = ArcadeDriveCalculator.INSTANCE;
		}
	}
}
