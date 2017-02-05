package org.usfirst.frc.team5818.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;
import org.usfirst.frc.team5818.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.CANTalon;

public class Turret extends Subsystem implements PIDSource, PIDOutput {
	
	private CANTalon motor;
	
	private PIDSourceType pidType = PIDSourceType.kDisplacement;
	private BetterPIDController angController;
	private AnalogInput pot;
	
	
	public int centerOffSet;
	public double potScale;
	public static final double kP = 0.011;
	public static final double kI = 0.0005;
	public static final double kD = 0.0;

	
	public Turret() {
		motor = new CANTalon(RobotMap.TURR_MOTOR); //Turret motor number not set
		motor.setInverted(true);
		angController  = new BetterPIDController(kP,kI, kD, this, this);
		pot = new AnalogInput(BotConstants.TURRET_POT);
		angController.setAbsoluteTolerance(0.3);
		centerOffSet = 992;
		potScale = 90.0/(2557-992);
	}
	
	public void setPower(double x) {
		angController.disable();
		pidWrite(x);
	}
	
	public void setAng(double ang) {
		angController.disable();
		angController.setSetpoint(ang);
		angController.enable();
	}
	
	public double getAng() {
		double analog = pot.getValue();
		return ((analog - centerOffSet) * potScale);
	}
	
	public double getRawCounts(){
		return pot.getValue();
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
		if(getAng() > 120){
			if(x < 0){
				motor.set(x);
			}
		    else{
		    	motor.set(0.0);
		    }
		}
		else if(getAng() < -45){
			if(x > 0){
				motor.set(x);
			}
			else{
				motor.set(0.0);
			}
		}
		else{
			motor.set(x);
		}
	}

	@Override
	protected void initDefaultCommand() {}
}
