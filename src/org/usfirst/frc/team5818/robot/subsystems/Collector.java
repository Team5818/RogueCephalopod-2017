package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.CollectorControlCommand;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Collector extends Subsystem implements PIDSource, PIDOutput {

    private static final double kP = 0.0;// tune me pls
    private static final double kI = 0.0;
    private static final double kD = 0.0;

    private CANTalon leftMotorTal;
    private CANTalon rightMotorTal;

    public PIDSourceType pidType = PIDSourceType.kDisplacement;
    public BetterPIDController anglePID;

    public int angleOffset;

    public Collector() {
        leftMotorTal = new CANTalon(RobotMap.ARM_TALON_L);
        leftMotorTal.setInverted(true);
        leftMotorTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        rightMotorTal = new CANTalon(RobotMap.ARM_TALON_R);
        rightMotorTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        anglePID = new BetterPIDController(kP, kI, kD, this, this);
        anglePID.setAbsoluteTolerance(0.3);
        angleOffset = 370;
    }

    public void setPower(double x) {
        if (anglePID.isEnabled()) {
            anglePID.disable();
        }
        leftMotorTal.set(x * BotConstants.MAX_POWER);
        rightMotorTal.set(x * BotConstants.MAX_POWER);
    }

    public void setAngle(double angle) {
        anglePID.disable();
        anglePID.setSetpoint(angle);
        anglePID.enable();
    }

    public double getRaw(){
        return rightMotorTal.getEncPosition();
    }
    
    public double getAngle() {
        return (rightMotorTal.getEncPosition() - angleOffset)/4096.0*360.0/2.0;
    }

    public BetterPIDController getAnglePID() {
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
        leftMotorTal.set(x);
        rightMotorTal.set(x);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CollectorControlCommand());
    }

}
