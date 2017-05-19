package org.usfirst.frc.team5818.robot.subsystems;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.TurretControlCommand;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret implements PIDSource, PIDOutput {
    /**
     * Subsystem representing turreted gear placer. Has 2-stage
     * pneumatic extender and turreted base. 
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

    public static final double kP = 0.03;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double CENTER_OFFSET = Constant.turretCenter();
    public static final double POT_SCALE = Constant.turretScale();

    public final Subsystem rotator = new Rotator();
    public final Subsystem deployer = new Deployer();

    private CANTalon motor;

    private PIDSourceType pidType = PIDSourceType.kDisplacement;
    private BetterPIDController angleController;
    private AnalogInput pot;
    private DigitalInput limitSwitch;

    private Solenoid puncher;
    private Solenoid extender;

    public Turret() {
        pot = new AnalogInput(RobotMap.TURRET_POT);
        motor = new CANTalon(RobotMap.TURR_MOTOR);
        motor.setInverted(true);
        angleController = new BetterPIDController(kP, kI, kD, this, this);
        limitSwitch = new DigitalInput(RobotMap.TURRET_LIMIT_SWITCH);
        angleController.setAbsoluteTolerance(0.3);
        puncher = new Solenoid(RobotMap.TURRET_PUNCHER_SOLENOID);
        extender = new Solenoid(RobotMap.TURRET_EXTENDER_SOLENOID);
    }

    public void setPower(double x) {
        if (angleController.isEnabled()) {
            angleController.disable();
        }
        pidWrite(x);
    }

    public boolean getLimit() {
        return limitSwitch.get();
    }

    public void setAngle(double ang) {
        angleController.disable();
        angleController.setSetpoint(ang);
        angleController.enable();
    }

    public double getAngle() {
        double analog = pot.getValue();
        return ((analog - CENTER_OFFSET) * POT_SCALE);
    }

    public double getRawCounts() {
        return pot.getValue();
    }

    public BetterPIDController getAngleController() {
        return angleController;
    }

    public void stop() {
        angleController.disable();
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
        /*soft limits on turret position*/
        if (getAngle() > 60) {
            x = Math.min(x, 0);
        } else if (getAngle() < -60) {
            x = Math.max(0, x);
        }
        motor.set(x);
    }

    /*pneumatics control*/
    public void extend(boolean on) {
        extender.set(on);
    }

    public void punch(boolean on) {
        puncher.set(on);
    }

}
