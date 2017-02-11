package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.AimTurret;
import org.usfirst.frc.team5818.robot.commands.DriveStraight;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.SwitchDriveMode;
import org.usfirst.frc.team5818.robot.commands.SwitchFeed;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.TankDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {
	Joystick JS_FW_BACK;
	Joystick JS_TURN;
	Joystick JS_TURRET;
	DriveTrain train;
	public static double  JOYSTICK_DEADBAND = .2;
	public static boolean joystickControlEnabled;
	
	public boolean driving = true;
	public boolean was_driving;

	public boolean turreting = true;
	public boolean was_turreting;

	
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
		JS_TURRET = new Joystick(BotConstants.JS_TURRET);
		
		JoystickButton killPi = new JoystickButton(JS_FW_BACK, 4);
		killPi.whenPressed(new ShutDownRPi());
		
		JoystickButton switchCam = new JoystickButton(JS_FW_BACK, 5);
		switchCam.whenPressed(new SwitchFeed());
		
		JoystickButton switchDrive = new JoystickButton(JS_FW_BACK, 6);
		switchDrive.whenPressed(new SwitchDriveMode());
		
		JoystickButton turret90 = new JoystickButton(JS_TURRET,1);
		turret90.whenPressed(new SetTurretAngle(90.0));
		
		JoystickButton turretZero = new JoystickButton(JS_TURRET,3);
		turretZero.whenPressed(new SetTurretAngle(0.0));
		
		JoystickButton turretAim = new JoystickButton(JS_TURRET,2);
		turretAim.whenPressed(new AimTurret());

		JoystickButton driveStraightButton = new JoystickButton(JS_FW_BACK, 3);
		driveStraightButton.whenPressed(new DriveStraight(24, 0.5, 0.97));
		
		train = Robot.runningrobot.driveTrain;
		dMode = DriveMode.POWER;
		cMode = ControlMode.ARCADE;
		driveCalc = ArcadeDriveCalculator.INSTANCE;
		joystickControlEnabled = true;
	}
	
	public void teleopPeriodic(){
	    if (joystickControlEnabled) {
            handleDeadbands();
            handleCalc();
            controlTurret();
            drive();
	    }
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
	
	public void controlTurret(){
		if(turreting){
			Robot.runningrobot.turret.setPower(JS_TURRET.getX()*.7);
		}
		else if(!turreting && was_turreting){
			Robot.runningrobot.turret.setPower(0.0);
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
	
	public void handleDeadbands(){
		was_driving = driving;
		driving = MathUtil.deadband(JS_FW_BACK, JOYSTICK_DEADBAND) || MathUtil.deadband(JS_TURN, JOYSTICK_DEADBAND);
		was_turreting = turreting;
		turreting = MathUtil.deadband(JS_TURRET, JOYSTICK_DEADBAND);
	}
}
