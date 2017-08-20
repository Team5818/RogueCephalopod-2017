package org.usfirst.frc.team5818.robot.subsystems;


import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.TurretControlCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret{
    
    public static final double TURRET_CENTER_POS = 0.0;
    public static final double TURRET_LEFT_POS = 0.0;
    public static final double TURRET_RIGHT_POS = 0.0;
    public static final double TURRET_LEFT_POS_SP = 0.0;
    public static final double TURRET_RIGHT_POS_SP = 0.0;

    
    /**
     * Subsystem representing turreted gear placer. Has 2-stage
     * pneumatic extender and turreted base. Rotates with motion magic.
     */

    private static final class Rotator extends Subsystem {
        /**
         * Rotating portion of turret
         */
        @Override
        protected void initDefaultCommand() {
            /*joystick control*/
            setDefaultCommand(new TurretControlCommand());
        }
    }

    private static final class Deployer extends Subsystem {
        /**
         * Gear placing portion of turret
         */
        @Override
        protected void initDefaultCommand() {
        }
    }


    public final Subsystem rotator = new Rotator();
    public final Subsystem deployer = new Deployer();

    private CANTalon motor;

    private DigitalInput limitSwitch;
    private Solenoid puncher;
    private Solenoid extender;

    public Turret() {
        motor = new CANTalon(RobotMap.TURR_MOTOR);
        motor.setInverted(true);
        motor.setForwardSoftLimit(TURRET_RIGHT_POS);
        motor.setReverseSoftLimit(TURRET_LEFT_POS);
        
        /*Set up motion profiling constants*/
        motor.configPotentiometerTurns(1);
        motor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        motor.setF(0.0);
        motor.setP(0.0);
        motor.setI(0);
        motor.setD(0.0);
        motor.setMotionMagicAcceleration(0.0);
        motor.setMotionMagicCruiseVelocity(0.0);//80% max
        motor.changeControlMode(TalonControlMode.MotionMagic);
        
        limitSwitch = new DigitalInput(RobotMap.TURRET_LIMIT_SWITCH);
        puncher = new Solenoid(RobotMap.TURRET_PUNCHER_SOLENOID);
        extender = new Solenoid(RobotMap.TURRET_EXTENDER_SOLENOID);
    }
    
    
    public double getVeleocity() {
        return motor.getSpeed();
    }


    public boolean getLimit() {
        return limitSwitch.get();
    }

    public void setAngle(double ang) {
        ang = ang/4096.0; //inputs in native units, just to make things harder
        motor.changeControlMode(TalonControlMode.MotionMagic);
        motor.set(ang);
    }


    public double getPositionRaw() {
        return motor.getAnalogInPosition();
    }
    
    public double getPosition(){
        return motor.getPosition();
    }


    public void setPower(double x) {
        motor.changeControlMode(TalonControlMode.PercentVbus);
        motor.set(x);
    }
    
    public void setManual() {
        motor.changeControlMode(TalonControlMode.PercentVbus);
    }

    /*pneumatics control*/
    public void extend(boolean on) {
        extender.set(on);
    }

    public void punch(boolean on) {
        puncher.set(on);
    }

}
