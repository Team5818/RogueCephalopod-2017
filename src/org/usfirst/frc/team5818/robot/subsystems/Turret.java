package org.usfirst.frc.team5818.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;
import org.usfirst.frc.team5818.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.CANTalon;

public class Turret extends Subsystem implements PIDSource, PIDOutput {
	
	private CANTalon motor;
	
	public PIDSourceType pidType = PIDSourceType.kDisplacement;
	public BetterPIDController angController;
	
	public int centerOffSet;
	public static final double kP = 0.0;
	public static final double kI = 0.0;
	public static final double kD = 0.0;

	
	public Turret() {
		motor = new CANTalon(RobotMap.TURR_MOTOR); //Turret motor number not set
		angController  = new BetterPIDController(kP,kI, kD, this, this);
		
		angController.setAbsoluteTolerance(0.3);
		centerOffSet = 512;
	}
	
	public void setPower(double x) {
		angController.disable();
		motor.set(x * BotConstants.MAX_POWER);
	}
	
	public void setAng(double ang) {
		angController.disable();
		angController.setSetpoint(ang);
		angController.enable();
	}
	
	public double getAng() {
		double analog = motor.getAnalogInRaw();
		return ((analog - centerOffSet) * 360)/(1024);
	}
	
	public BetterPIDController getAngController() {
		return angController;
	}
	
	public void stop() {
		angController.disable();
		this.setPower(0);
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
	
	@Override
	public double pidGet() {
		return getAng();
	}
	
	@Override
	public void pidWrite(double x) {
		motor.set(x);
	}

	@Override
	protected void initDefaultCommand() {
		
	}

}
