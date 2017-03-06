package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.TurretControlCommand;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Turret extends Subsystem implements PIDSource, PIDOutput {

    public static final double kP = 0.03;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double CENTER_OFFSET = Preferences.getInstance().getDouble("TurretCenter", 1920);
    public static final double POT_SCALE = Preferences.getInstance().getDouble("TurretScale", -90.0 / 100.0);

    private CANTalon motor;

    private PIDSourceType pidType = PIDSourceType.kDisplacement;
    private BetterPIDController angleController;
    private AnalogInput pot;
    private DigitalInput limitSwitch;

    private Solenoid puncher;
    private Solenoid extender;
    private Solenoid leftMini;
    private Solenoid rightMini;
    private Side currentMini;

    public Turret() {
        motor = new CANTalon(RobotMap.TURR_MOTOR);
        motor.setInverted(true);
        angleController = new BetterPIDController(kP, kI, kD, this, this);
        limitSwitch = new DigitalInput(5);
        pot = new AnalogInput(BotConstants.TURRET_POT);
        angleController.setAbsoluteTolerance(0.3);
        puncher = new Solenoid(RobotMap.TURRET_PUNCHER_SOLENOID);
        extender = new Solenoid(RobotMap.TURRET_EXTENDER_SOLENOID);
        leftMini = new Solenoid(RobotMap.LEFT_MINI_SOLENOID);
        rightMini = new Solenoid(RobotMap.RIGHT_MINI_SOLENOID);
        currentMini = Side.LEFT;
    }

    public void setPower(double x) {
        if (angleController.isEnabled()) {
            angleController.disable();
        }
        pidWrite(x);
    }
    
    public boolean getLimit(){
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
        if (getAngle() > 100) {
            x = Math.min(x, 0);
        } else if (getAngle() < -100) {
            x = Math.max(0, x);
        }
        motor.set(x);
    }

    public void extend(boolean on) {
        extender.set(on);
    }

    public void punch(boolean on) {
        puncher.set(on);
    }

    public void leftMini(boolean on) {
        leftMini.set(on);
    }

    public void rightMini(boolean on) {
        rightMini.set(on);
    }
    
    public void currentMini(boolean on){
        if(currentMini.equals(Side.LEFT)){
            leftMini(on);
        }
        else if(currentMini.equals(Side.RIGHT)){
            rightMini(on);
        }
    }
    
    public void switchCurrentMini(Side s){
        currentMini = s;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TurretControlCommand());
    }
}
