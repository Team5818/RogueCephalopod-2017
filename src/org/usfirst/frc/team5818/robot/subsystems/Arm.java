package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem implements PIDSource, PIDOutput {

    /**
     * Subsystem for robot's arm. Has 2 775s and a Vex absolute encoder.
     * Uses PD positioning control. 
     */
    
    /*PID Constants*/
    private static final double kP = 0.0006;
    private static final double kI = 0.0000;
    private static final double kD = 0.000045;

    /*Important positions and angles*/
    private static final double COLLECT_ANGLE = 11;
    public static final double COLLECT_POSITION = 2371;
    public static final double CLIMB_POSITION = .93;
    public static final double MID_POSITION = 3829;
    public static final double NINETY_DEGREES = 1.0;
    public static final double SLOT_COLLECT_POSITION = NINETY_DEGREES;
    public static final double TURRET_RESET_POSITION = NINETY_DEGREES;
    public static final double LOAD_POSITION = 1.19;
    
    /*Calculate scales and offsets*/
    public static final double ANGLE_SCALE = (90 - COLLECT_ANGLE) / (NINETY_DEGREES - COLLECT_POSITION);
    public static final double ANGLE_OFFSET = (COLLECT_ANGLE - (COLLECT_POSITION * ANGLE_SCALE)) - 16.3;
    
    /*Minimum power to keep it from falling at collect angle*/
    public static final double HOLD_POWER = .055;
    
    /*soft limits on arm position*/
    private double limitLow = COLLECT_POSITION;
    private double limitHigh = LOAD_POSITION;

    /*Talons + PID stuff*/
    private CANTalon slaveTal;
    private CANTalon masterTal;
    public PIDSourceType pidType = PIDSourceType.kDisplacement;
    public BetterPIDController anglePID;


    public Arm() {
        masterTal = new CANTalon(RobotMap.ARM_TALON_R);
        masterTal.setInverted(true);
        slaveTal = new CANTalon(RobotMap.ARM_TALON_L);
        slaveTal.changeControlMode(TalonControlMode.Follower);
        slaveTal.set(RobotMap.ARM_TALON_R);
        slaveTal.reverseOutput(true);
        
        /*use absolute encoder for an absolute position*/
        //masterTal.configEncoderCodesPerRev(4096*2);
        masterTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        masterTal.setF(1023.0/360.0);
        masterTal.setP(0);
        masterTal.setI(0);
        masterTal.setD(0);
        masterTal.setMotionMagicAcceleration(30);
        masterTal.setMotionMagicCruiseVelocity(15);
        masterTal.changeControlMode(TalonControlMode.MotionMagic);
        
        anglePID = new BetterPIDController(kP, kI, kD, this, this);
        anglePID.setAbsoluteTolerance(0.3);
        setBrakeMode(true);
    }

    public void setBrakeMode(boolean mode) {
        slaveTal.enableBrakeMode(mode);
        masterTal.enableBrakeMode(mode);
    }

    public void setPower(double x) {
        if (anglePID.isEnabled()) {
            anglePID.disable();
        }
        pidWrite(x);
        SmartDashboard.putNumber("Arn Power", x);
    }
    
    public void setManual() {
        masterTal.changeControlMode(TalonControlMode.PercentVbus);
    }

    public void setAngle(double angle) {
        //anglePID.disable();
        //anglePID.setSetpoint(angle);
        //anglePID.enable();
        angle = angle/4096.0/2.0;
        //masterTal.setEncPosition(masterTal.getPulseWidthPosition());
        masterTal.changeControlMode(TalonControlMode.MotionMagic);
        masterTal.set(angle);
    }

    public double getPositionRaw() {
        return masterTal.getPulseWidthPosition();
    }
    
    public double getPosition() {
        masterTal.setEncPosition(masterTal.getPulseWidthPosition());
        return masterTal.getPosition();
    }
    
    public double getVeleocity() {
        return masterTal.getSpeed();
    }

    public BetterPIDController getAnglePID() {
        return anglePID;
    }

    public void stop() {
        if (anglePID.isEnabled()) {
            anglePID.disable();
        }
        setBrakeMode(true);
        //slaveTal.set(0);
        masterTal.set(0);
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
        return getPosition();
    }

    public double getIdlePower() {
        /*calculate the power needed to keep the arm still at the current angle*/
        SmartDashboard.putNumber("Arm Angle", Math.toRadians((getPosition() * ANGLE_SCALE + ANGLE_OFFSET)));
        return HOLD_POWER * Math.cos(Math.toRadians((getPosition() * ANGLE_SCALE + ANGLE_OFFSET)));
    }

    public void setLimitLow(double limitLow) {
        this.limitLow = limitLow;
    }

    public void setLimitHigh(double limitHigh) {
        this.limitHigh = limitHigh;
    }
    
    public int getError() {
        return masterTal.getClosedLoopError();
    }

    @Override
    public void pidWrite(double x) {
        /*Obey soft limits*/
//        if (getPosition() <= limitLow) {
//            x = Math.max(x, 0);
//        } else if (getPosition() >= limitHigh) {
//            x = Math.min(x, 0);
//        }
        /*add idle power to prevent arm from falling*/
        //slaveTal.set(x + getIdlePower());
        masterTal.set(x);
        SmartDashboard.putNumber("Arm Power", x);

    }

    @Override
    protected void initDefaultCommand() {
    }

}