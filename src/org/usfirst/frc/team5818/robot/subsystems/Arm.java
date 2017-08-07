package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem{
    /**
     * Subsystem for robot's arm. Has 2 775s and a Vex absolute encoder.
     * Uses Motion Magic to move. 
     */
    

    /*Important positions and angles*/
    public static final double COLLECT_POSITION = 2300;
    public static final double CLIMB_POSITION = 2400;
    public static final double MID_POSITION = 3829;
    public static final double NINETY_DEGREES = 3900;
    public static final double SLOT_COLLECT_POSITION = NINETY_DEGREES;
    public static final double TURRET_RESET_POSITION = NINETY_DEGREES;
    public static final double LOAD_POSITION = 4906;
    
    
    /*soft limits on arm position*/
    private double limitLow = COLLECT_POSITION;
    private double limitHigh = LOAD_POSITION;

    /*Talons*/
    private CANTalon slaveTal;
    private CANTalon masterTal;

    public Arm() {
        masterTal = new CANTalon(RobotMap.ARM_TALON_R);
        masterTal.setInverted(true);
        masterTal.reverseOutput(true);
        masterTal.setForwardSoftLimit(limitHigh);
        masterTal.setReverseSoftLimit(limitLow);
        slaveTal = new CANTalon(RobotMap.ARM_TALON_L);
        slaveTal.changeControlMode(TalonControlMode.Follower);
        slaveTal.set(RobotMap.ARM_TALON_R);
        slaveTal.reverseOutput(true);
        
        /*Set up motion profiling constants*/
        masterTal.configEncoderCodesPerRev(4096*2);
        masterTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        masterTal.setF(1023.0/360.0);
        masterTal.setP(2.4 * 1023.0 / 1000);
        masterTal.setI(0);
        masterTal.setD(50.0 * 1023.0 / 1000);
        masterTal.setMotionMagicAcceleration(60);
        masterTal.setMotionMagicCruiseVelocity(40);
        masterTal.changeControlMode(TalonControlMode.MotionMagic);
        
        setBrakeMode(true);
    }

    public void setBrakeMode(boolean mode) {
        slaveTal.enableBrakeMode(mode);
        masterTal.enableBrakeMode(mode);
    }

    public void setPower(double x) {
        masterTal.changeControlMode(TalonControlMode.PercentVbus);
        masterTal.set(x);
        SmartDashboard.putNumber("Arm Power", x);
    }
    
    public void setManual() {
        masterTal.changeControlMode(TalonControlMode.PercentVbus);
    }

    public void setAngle(double angle) {
        angle = angle/4096.0;
        DriverStation.reportError("" + angle, false);
        masterTal.setEncPosition(masterTal.getPulseWidthPosition());
        setBrakeMode(false);
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


    public void stop() {
        setBrakeMode(true);
        setManual();
        masterTal.set(0);
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
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }


}