package org.usfirst.frc.team5818.controllers;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.SwitchControlMode;
import org.usfirst.frc.team5818.robot.commands.SwitchDriveMode;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

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
	JoystickButton arcadeControl;
	JoystickButton tankControl;
	JoystickButton radiusControl;
	JoystickButton quickTurn;
	JoystickButton powerDrive;
	JoystickButton velocityDrive;
	DriveTrain train;
	DriveCalculator driveCalc;
	double deadband = .2;
	
	
	public enum DriveMode{
		POWER, VELOCITY, STOPPED
	}
	
	public enum ControlMode{
		ARCADE, TANK, RADIUS, QUICK_RADIUS
	}
	
	public DriveMode dMode;
	public ControlMode cMode;
	
	public Driver() {
		train = Robot.runningrobot.driveTrain;
		
		JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
		JS_TURN = new Joystick(BotConstants.JS_TURN);
		
		arcadeControl = new JoystickButton(JS_FW_BACK, 2);
		tankControl   = new JoystickButton(JS_FW_BACK, 3);
		radiusControl = new JoystickButton(JS_FW_BACK, 4);
		quickTurn     = new JoystickButton(JS_TURN, 2);
		powerDrive    = new JoystickButton(JS_TURN, 3);
		velocityDrive = new JoystickButton(JS_TURN, 4);
		
		arcadeControl.whenPressed(new SwitchControlMode(ControlMode.ARCADE));
		tankControl.whenPressed(new SwitchControlMode(ControlMode.TANK));
		radiusControl.whenPressed(new SwitchControlMode(ControlMode.RADIUS));
		quickTurn.whenPressed(new SwitchControlMode(ControlMode.QUICK_RADIUS));
		velocityDrive.whenPressed(new SwitchDriveMode(DriveMode.VELOCITY));
		powerDrive.whenPressed(new SwitchDriveMode(DriveMode.POWER));

		dMode = DriveMode.POWER;
		cMode = ControlMode.ARCADE;
	}
	
	public void teleopPeriodic(){
		handleCalc();
		drive();
	}
	
	public void drive(){
		Vector2d driveVector = Vectors.fromJoystick(JS_FW_BACK, JS_TURN, true);
		Vector2d arcadeVector = driveCalc.compute(driveVector);
		switch(dMode){
		    case POWER:
			    train.setPowerLeftRight(arcadeVector);
			    break;
		    case VELOCITY:
			    train.setVelocityLeftRight(arcadeVector.scale(BotConstants.ROBOT_MAX_VELOCITY));
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
	    		break;
	    	default:
	    		driveCalc = ArcadeDriveCalculator.INSTANCE;
	    		break;
		}
	}
}
