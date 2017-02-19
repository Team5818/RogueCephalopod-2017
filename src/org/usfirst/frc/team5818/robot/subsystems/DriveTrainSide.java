package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrainSide extends Subsystem implements PIDSource, PIDOutput {

    public static final double L_VEL_KP = 0.015; // NEEDS TUNING
    public static final double L_VEL_KI = 0.0; // NEEDS TUNING
    public static final double L_VEL_KD = 0.0; // NEEDS TUNING
    public static final double L_VEL_KF = 0.0; // NEEDS TUNING

    public static final double L_DIST_KP = 0.005; // NEEDS TUNING
    public static final double L_DIST_KI = 0.0001; // NEEDS TUNING
    public static final double L_DIST_KD = 0.0; // NEEDS TUNING

    public static final double R_VEL_KP = 0.001; // NEEDS TUNING
    public static final double R_VEL_KI = 0.0; // NEEDS TUNING
    public static final double R_VEL_KD = 0.0; // NEEDS TUNING
    public static final double R_VEL_KF = 0.0; // NEEDS TUNING

    public static final double R_DIST_KP = 0.005; // NEEDS TUNING
    public static final double R_DIST_KI = 0.0001; // NEEDS TUNING
    public static final double R_DIST_KD = 0.0; // NEEDS TUNING

    private CANTalon motorNoEnc;
    private CANTalon motorEnc;
    private CANTalon motor2NoEnc;

    public BetterPIDController distController;
    public BetterPIDController velController;

    public PIDSourceType pidType = PIDSourceType.kDisplacement;

    private int encoderSign;
    private Side side;

    public DriveTrainSide(Side side) {
        if (side == Side.CENTER) {
            throw new IllegalArgumentException("A drive train may not be in the center");
        }
        this.side = side;
        if (side == Side.LEFT) {
            encoderSign = 1;
            motorNoEnc = new CANTalon(RobotMap.L_TALON);
            motorEnc = new CANTalon(RobotMap.L_TALON_ENC);
            motor2NoEnc = new CANTalon(RobotMap.L_TALON_2);
            motorNoEnc.setInverted(false);
            motorEnc.setInverted(true);
            motor2NoEnc.setInverted(false);
            velController = new BetterPIDController(L_VEL_KP, L_VEL_KI,
                    L_VEL_KD, L_VEL_KF, this, this);
            distController = new BetterPIDController(L_DIST_KP, L_DIST_KI,
                    L_DIST_KD, this, this);
        } else {
            encoderSign = -1;
            motorNoEnc = new CANTalon(RobotMap.R_TALON);
            motorEnc = new CANTalon(RobotMap.R_TALON_ENC);
            motor2NoEnc = new CANTalon(RobotMap.R_TALON_2);
            motorNoEnc.setInverted(true);
            motorEnc.setInverted(false);
            motor2NoEnc.setInverted(true);
            velController = new BetterPIDController(R_VEL_KP, R_VEL_KI,
                    R_VEL_KD, R_VEL_KF, this, this);
            distController = new BetterPIDController(R_DIST_KP, R_DIST_KI,
                    R_DIST_KD, this, this);

        }
        distController.setAbsoluteTolerance(1);

    }

    public void setPower(double numIn) {
        if (velController.isEnabled()) {
            velController.disable();
        }
        if (distController.isEnabled()) {
            distController.disable();
        }
        motorNoEnc.set(numIn * BotConstants.MAX_POWER);
        motorEnc.set(numIn * BotConstants.MAX_POWER);
        motor2NoEnc.set(numIn * BotConstants.MAX_POWER);
    }

    public double getSidePosition() {
        return encoderSign * motorEnc.getEncPosition()
                / BotConstants.TICS_PER_INCH;
    }

    public double getSideVelocity() {
        return encoderSign * motorEnc.getEncVelocity()
                / BotConstants.TICS_PER_INCH;
    }

    @Override
    public void pidWrite(double val) {
        motorEnc.set(val * BotConstants.MAX_POWER);
        motorNoEnc.set(val * BotConstants.MAX_POWER);
        motor2NoEnc.set(val * BotConstants.MAX_POWER);
    }

    @Override
    public double pidGet() {
        if (pidType == PIDSourceType.kDisplacement) {
            return getSidePosition();
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

    public void driveDistance(double dist) {
        pidType = PIDSourceType.kDisplacement;
        velController.disable();
        distController.disable();
        resetEnc();
        distController.setSetpoint(dist);
        distController.enable();
    }

    public void driveVelocity(double dist) {
        pidType = PIDSourceType.kRate;
        velController.disable();
        distController.disable();
        resetEnc();
        velController.setSetpoint(dist);
        velController.enable();
    }

    public BetterPIDController getVelController() {
        return velController;
    }

    public BetterPIDController getDistController() {
        return distController;
    }

    public void setMaxPower(double max) {
        distController.setInputRange(-max, max);
    }

    public void resetEnc() {
        motorEnc.setEncPosition(0);
    }

    public void setCoastMode() {
        motorNoEnc.enableBrakeMode(false);
        motorEnc.enableBrakeMode(false);
        motor2NoEnc.enableBrakeMode(false);
    }

    public void setBrakeMode() {
        motorEnc.enableBrakeMode(true);
        motorNoEnc.enableBrakeMode(true);
        motor2NoEnc.enableBrakeMode(true);
    }

    public void initDefaultCommand() {

    }
}
