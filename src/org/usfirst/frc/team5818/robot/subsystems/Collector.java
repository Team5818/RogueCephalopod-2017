package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.CollectorControlCommand;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Collector extends Subsystem implements PIDSource, PIDOutput {

    private static final double kP = 0.0004;// tune me pls
    private static final double kI = 0.0000;
    private static final double kD = 0.0;

    public static final double COLLECT_POSITION = -5;
    public static final double MID_POSITION = 1538;
    public static final double TURRET_RESET_POSITION = 2000;
    public static final double LOAD_POSITION = 2782;
    public static final double angleScale = .04277;
    public static final double angleOffset = 11.21385 - 16.3;
    public static final double holdPower = .055;

    private CANTalon leftMotorTal;
    private CANTalon rightMotorTal;

    public PIDSourceType pidType = PIDSourceType.kDisplacement;
    public BetterPIDController anglePID;

    public Collector() {
        leftMotorTal = new CANTalon(RobotMap.ARM_TALON_L);
        leftMotorTal.setInverted(false);
        leftMotorTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        rightMotorTal = new CANTalon(RobotMap.ARM_TALON_R);
        rightMotorTal.setInverted(true);
        rightMotorTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        anglePID = new BetterPIDController(kP, kI, kD, this, this);
        anglePID.setAbsoluteTolerance(0.3);
        setBrakeMode(true);
    }

    public void setBrakeMode(boolean mode) {
        leftMotorTal.enableBrakeMode(mode);
        rightMotorTal.enableBrakeMode(mode);
    }

    public void setPower(double x) {
        if (anglePID.isEnabled()) {
            anglePID.disable();
        }
        pidWrite(x);
        SmartDashboard.putNumber("Arn Power", x);
    }

    public void setAngle(double angle) {
        anglePID.disable();
        anglePID.setSetpoint(angle);
        anglePID.enable();
    }

    public double getPosition() {
        return rightMotorTal.getEncPosition();
    }

    public BetterPIDController getAnglePID() {
        return anglePID;
    }

    public void stop() {
        if (anglePID.isEnabled()) {
            anglePID.disable();
        }
        setBrakeMode(true);
        leftMotorTal.set(0);
        rightMotorTal.set(0);
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
        SmartDashboard.putNumber("Arm Angle", Math.toRadians((getPosition() * angleScale + angleOffset)));
        return holdPower * Math.cos(Math.toRadians((getPosition() * angleScale + angleOffset)));
    }

    @Override
    public void pidWrite(double x) {
        if (getPosition() <= COLLECT_POSITION) {
            x = Math.max(x, 0);
        } else if (getPosition() >= LOAD_POSITION) {
            x = Math.min(x, 0);
        }
        leftMotorTal.set(x + getIdlePower());
        rightMotorTal.set(x + getIdlePower());
        SmartDashboard.putNumber("Arn Power", x);

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CollectorControlCommand());
    }

}
