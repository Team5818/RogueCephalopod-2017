package org.usfirst.frc.team5818.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;
import org.usfirst.frc.team5818.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.CANTalon;

public class CollectorArmSide extends Subsystem implements PIDSource, PIDOutput {
	
	private CANTalon motorTal;
	
	public PIDSourceType pidType = PIDSourceType.kDisplacement;
	public BetterPIDController anglePID;
	
	public int centerOffSet;
	
	public enum Side {
		RIGHT, LEFT;
	}
	
	private Side side;
	
	public CollectorArmSide(boolean inverted) {
		if(inverted) {
			motorTal = new CANTalon(RobotMap.ARM_TALON_L);
			this.side = Side.LEFT;
			anglePID  = new BetterPIDController(BotConstants.L_COL_KP,BotConstants.L_COL_KI, BotConstants.L_COL_KD, this, this);
		} else {
			motorTal = new CANTalon(RobotMap.ARM_TALON_R);
			this.side = Side.RIGHT;
			anglePID  = new BetterPIDController(BotConstants.R_COL_KP,BotConstants.R_COL_KI, BotConstants.R_COL_KD, this, this);
		}
		motorTal.setInverted(inverted);
		anglePID.setAbsoluteTolerance(0.3);
		centerOffSet = 512;
	}
	
	public void setPower(double x) {
		anglePID.disable();
		motorTal.set(x * BotConstants.MAX_POWER);
	}
	
	public void setAngle(double angle) {
		anglePID.disable();
		anglePID.setSetpoint(angle);
		anglePID.enable();
	}
	
	public double getAngle() {
		double analog = motorTal.getAnalogInRaw();
		return ((analog - centerOffSet) * 360)/(1024);
	}
	
	public BetterPIDController getanglePID() {
		return anglePID;
	}
	
	public void stop() {
		anglePID.disable();
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
		return getAngle();
	}
	
	@Override
	public void pidWrite(double x) {
		motorTal.set(x);
	}

	@Override
	protected void initDefaultCommand() {
		
	}

}
