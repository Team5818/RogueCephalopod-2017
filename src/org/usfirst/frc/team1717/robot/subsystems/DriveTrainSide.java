package org.usfirst.frc.team1717.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;

import org.usfirst.frc.team1717.robot.RobotMap;
import org.usfirst.frc.team1717.robot.constants.BotConstants;

public class DriveTrainSide extends Subsystem implements PIDSource, PIDOutput {
	public enum Side {
		RIGHT, LEFT;
	}
	
	private CANTalon motorNoEnc;
	private CANTalon motorEnc;
	
	public PIDController distController;
	public PIDController velController;

	
	public PIDSourceType pidType = PIDSourceType.kDisplacement;
	
	public DriveTrainSide(Side side) {
		if (side == Side.LEFT) {
			motorNoEnc = new CANTalon(RobotMap.L_TALON);
			motorEnc = new CANTalon(RobotMap.L_TALON_ENC);
			velController = new PIDController(BotConstants.L_VEL_KP, BotConstants.L_VEL_KI,
					BotConstants.L_VEL_KD, BotConstants.L_VEL_KF, this, this);
			distController = new PIDController(BotConstants.L_DIST_KP, BotConstants.L_DIST_KI,
					BotConstants.L_DIST_KD, this, this);
		} else {
			motorNoEnc = new CANTalon(RobotMap.R_TALON);
			motorEnc = new CANTalon(RobotMap.R_TALON_ENC);
			motorNoEnc.setInverted(true);
			motorEnc.setInverted(true);
			velController = new PIDController(BotConstants.R_VEL_KP, BotConstants.R_VEL_KI,
					BotConstants.R_VEL_KD, BotConstants.R_VEL_KF, this, this);
			distController = new PIDController(BotConstants.R_DIST_KP, BotConstants.R_DIST_KI,
					BotConstants.R_DIST_KD, this, this);

		}

	}
	public void setPower(double numIn) {
		velController.disable();
		distController.disable();
		motorNoEnc.set(numIn * BotConstants.MAX_POWER);
		motorEnc.set(numIn * BotConstants.MAX_POWER);
	}

	public double getSidePosition() {
		return motorEnc.getEncPosition() / BotConstants.TICS_PER_INCH;
	}
	
	public double getSideVelocity(){
		return motorEnc.getEncVelocity() / BotConstants.TICS_PER_INCH;
	} 

	@Override
	public void pidWrite(double val) {
		motorEnc.set(val * BotConstants.MAX_POWER);
		motorNoEnc.set(val * BotConstants.MAX_POWER);
	}
	@Override
	public double pidGet() {
		if (pidType == PIDSourceType.kDisplacement) {
			return motorEnc.getEncPosition();
		} else {
			return motorEnc.getEncVelocity();
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidType = pidSource;
	}
	@Override
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}
	
	public void driveDistance(double dist){
		pidType = PIDSourceType.kDisplacement;
		velController.disable();
		distController.disable();
		resetEnc();
		distController.setSetpoint(dist);
		distController.enable();
	}
	
	public void driveVelocity(double dist){
		pidType = PIDSourceType.kRate;
		velController.disable();
		distController.disable();
		resetEnc();
		velController.setSetpoint(dist);
		velController.enable();
	}


	public void resetEnc(){
		motorEnc.setEncPosition(0);
	}
	public void setCoastMode() {
		motorNoEnc.enableBrakeMode(false);
		motorEnc.enableBrakeMode(false);
	}
	
	public void setBrakeMode() {
		motorEnc.enableBrakeMode(true);
		motorNoEnc.enableBrakeMode(true);
	}
	
	public void initDefaultCommand() {
		
	}
}
