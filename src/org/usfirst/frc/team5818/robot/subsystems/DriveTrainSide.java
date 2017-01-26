package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;
import org.usfirst.frc.team5818.robot.constants.BotConstants;

public class DriveTrainSide extends Subsystem implements PIDSource, PIDOutput {
	public enum Side {
		RIGHT, LEFT;
	}
	
	private CANTalon motorNoEnc;
	private CANTalon motorEnc;
	
	public PIDController distController;
	public PIDController velController;

	public double KP = 1.0; // NEEDS TUNING
	public double KI = 1.0;
	public double KD = 1.0;
	public double KF = 1.0;
	public PIDSourceType pidType = PIDSourceType.kDisplacement;
	
	public DriveTrainSide(Side side) {
		if (side == Side.LEFT) {
			motorNoEnc = new CANTalon(RobotMap.L_TALON);
			motorEnc = new CANTalon(RobotMap.L_TALON_ENC);
		} else {
			motorNoEnc = new CANTalon(RobotMap.R_TALON);
			motorEnc = new CANTalon(RobotMap.R_TALON_ENC);
		}
		
		velController = new PIDController(KP, KI, KD, KF, this, this);
		distController = new PIDController(KP, KI, KD, this, this);

	}
	public void setPower(double numIn) {
		motorNoEnc.set(numIn * BotConstants.POWER_MULTIPLIER);
		motorEnc.set(numIn * BotConstants.POWER_MULTIPLIER);
	}

	public int getSidePosition() {
		return motorEnc.getEncPosition();
	}

	@Override
	public void pidWrite(double val) {
		motorEnc.set(val * BotConstants.POWER_MULTIPLIER);
	}
	@Override
	public double pidGet() {
		if (pidType == PIDSourceType.kDisplacement) {
			return motorEnc.getEncPosition();
		} else {
			return motorEnc.getEncVelocity();
		}
	}

	public void initDefaultCommand() {
		
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}
	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
}
