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

	public double VEL_KP = 1.0; // NEEDS TUNING
	public double VEL_KI = 1.0; // NEEDS TUNING
	public double VEL_KD = 1.0; // NEEDS TUNING
	public double VEL_KF = 1.0; // NEEDS TUNING
	
	public double DIST_KP = 1.0; // NEEDS TUNING
	public double DIST_KI = 1.0; // NEEDS TUNING
	public double DIST_KD = 1.0; // NEEDS TUNING
	
	public PIDSourceType pidType = PIDSourceType.kDisplacement;
	
	public DriveTrainSide(Side side) {
		if (side == Side.LEFT) {
			motorNoEnc = new CANTalon(RobotMap.L_TALON);
			motorEnc = new CANTalon(RobotMap.L_TALON_ENC);
		} else {
			motorNoEnc = new CANTalon(RobotMap.R_TALON);
			motorEnc = new CANTalon(RobotMap.R_TALON_ENC);
			motorNoEnc.setInverted(true);
			motorEnc.setInverted(true);

		}
		
		velController = new PIDController(VEL_KP, VEL_KI, VEL_KD, VEL_KF, this, this);
		distController = new PIDController(DIST_KP, DIST_KI, DIST_KD, this, this);

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
