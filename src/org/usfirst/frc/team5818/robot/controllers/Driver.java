package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.SwitchDriveMode;
import org.usfirst.frc.team5818.robot.commands.SwitchFeed;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.TankDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {
	Joystick JS_FW_BACK;
	Joystick JS_TURN;
	DriveTrain train;
	double deadband = .2;
	public static final int BUT_QUICK_TURN = 2;
	
	
	public enum DriveMode{
		POWER, VELOCITY, 
	}
	
	public enum ControlMode{
		ARCADE, TANK, RADIUS
	}
	
	public DriveMode dMode;
	public ControlMode cMode;
	private DriveCalculator driveCalc;
	
	public Driver() {
		JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
		JS_TURN = new Joystick(BotConstants.JS_TURN);
		
		JoystickButton killPi = new JoystickButton(JS_FW_BACK, 4);
		killPi.whenPressed(new ShutDownRPi());
		
		JoystickButton switchCam = new JoystickButton(JS_FW_BACK, 5);
		switchCam.whenPressed(new SwitchFeed());
		
		JoystickButton switchDrive = new JoystickButton(JS_FW_BACK, 6);
		switchDrive.whenPressed(new SwitchDriveMode());
		
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
		RadiusDriveCalculator.INSTANCE.setQuick(JS_TURN.getRawButton(BUT_QUICK_TURN));
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
		    default:
		    	driveCalc = ArcadeDriveCalculator.INSTANCE;
		}
	}
}
