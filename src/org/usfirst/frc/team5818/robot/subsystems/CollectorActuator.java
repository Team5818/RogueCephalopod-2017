package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CollectorActuator implements PIDSource, PIDOutput{
	private CANTalon motorTalL;
	private CANTalon motorTalR;
	private double angleOffset = 0;
	private BetterPIDController anglePID;
	private boolean PIDEnabled;
	
	private double maxPower = 1.0;

	private double maxAngle = 50;
	private double minAngle = 10;
	//Collector Arm PID constants
	private static final double KP = 0.0;
	private static final double KI = 0.0;
	private static final double KD = 0.0;
	
	public CollectorActuator() {
		motorTalL = new CANTalon(RobotMap.ARM_TALON_L);
		motorTalR = new CANTalon(RobotMap.ARM_TALON_R);
		motorTalL.setInverted(true);
		
		anglePID = new BetterPIDController(KP, KI, KD, this, this);
		anglePID.setOutputRange(-maxPower, maxPower);
        anglePID.setAbsoluteTolerance(0.5);
	
	}

	public void setPower(double power) {
		PIDEnabled = false;
		motorTalL.enableBrakeMode(true);
        motorTalR.enableBrakeMode(true);
		anglePID.disable();
        pidWrite(power);
	}
	
	public double getAngle() {
		//DO BLACK MAGIC ANGLE STUFF HERE
		
		return 0;
	}
	
	public void setAngle(double angle) {
        motorTalR.enableBrakeMode(false);
        motorTalL.enableBrakeMode(false);
        PIDEnabled = true;
        anglePID.reset();
        anglePID.setSetpoint(angle);
        anglePID.enable();
    }
	
	public void disablePID() {
        anglePID.disable();
        PIDEnabled = false;
    }
	
	public void stopThat() { //right now!
	    PIDEnabled = false;
		anglePID.disable();
		motorTalL.enableBrakeMode(true);
        motorTalR.enableBrakeMode(true);
    }
	
	@Override
	public void pidWrite(double power) {
		if(getAngle() <= minAngle) {
			motorTalL.set(-1*power);
			motorTalR.set(power);
		} else {
			motorTalL.set(power);
			motorTalR.set(-1*power);
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return getAngle();
	}
	
	public boolean getArmPIDEnabled() {
		return PIDEnabled;
	}
}
