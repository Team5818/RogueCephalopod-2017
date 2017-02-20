package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.TurretControlCommand;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem implements PIDSource, PIDOutput {

    public static final double kP = 0.011;
    public static final double kI = 0.0005;
    public static final double kD = 0.0;

    private CANTalon motor;

    private PIDSourceType pidType = PIDSourceType.kDisplacement;
    private BetterPIDController angleController;
    private AnalogInput pot;

    public int centerOffSet;
    public double potScale;

    private Solenoid solenoid1;
    private Solenoid solenoid2;

    public Turret() {
        motor = new CANTalon(RobotMap.TURR_MOTOR); // Turret motor number not
                                                   // set
        motor.setInverted(true);
        angleController = new BetterPIDController(kP, kI, kD, this, this);
        pot = new AnalogInput(BotConstants.TURRET_POT);
        angleController.setAbsoluteTolerance(0.3);
        centerOffSet = 3027;
        potScale = 360.0 / 4095.0;
        solenoid1 = new Solenoid(1);
        solenoid1 = new Solenoid(2);
    }

    public void setPower(double x) {
        if (angleController.isEnabled()) {
            angleController.disable();
        }
        pidWrite(x);
    }

    public void setAngle(double ang) {
        angleController.disable();
        angleController.setSetpoint(ang);
        angleController.enable();
    }

    public double getAngle() {
        double analog = pot.getValue();
        return ((analog - centerOffSet) * potScale);
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
        motor.set(x);
    }

    public void setSolenoid1(boolean on) {
        solenoid1.set(on);
    }

    public void setSolenoid2(boolean on) {
        solenoid2.set(on);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TurretControlCommand());
    }
}
