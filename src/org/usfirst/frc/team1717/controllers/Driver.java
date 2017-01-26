package org.usfirst.frc.team1717.controllers;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.commands.DriveForwardBack;
import org.usfirst.frc.team1717.robot.commands.DrivePowerDistance;
import org.usfirst.frc.team1717.robot.commands.ShutDownRPi;
import org.usfirst.frc.team1717.robot.constants.BotConstants;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
		JoystickButton driveBut = new JoystickButton(JS_FW_BACK, 1);
		driveBut.whenPressed(new DrivePowerDistance(1.0, 72, 1.0));
		JoystickButton driveBackwardsBut = new JoystickButton(JS_FW_BACK, 2);
		driveBackwardsBut.whenPressed(new DrivePowerDistance(-1.0, 72, 1.0));
		JoystickButton driveFBBut = new JoystickButton(JS_FW_BACK, 3);
		driveFBBut.whenPressed(new DriveForwardBack(0.5, 36, 1.0));
		JoystickButton killPi = new JoystickButton(JS_FW_BACK, 4);
		killPi.whenPressed(new ShutDownRPi());
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
            	train.stop();
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
