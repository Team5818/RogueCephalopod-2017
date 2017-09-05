package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.DriveControlCommand;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
    
    /**
     * Subsystem for drivetrain. 6-CIM West Coast drive.
     * Most logic takes place in DriveTrainSide
     */

    public DriveTrainSide left;
    public DriveTrainSide right;
    private Compressor comp;
    private Solenoid shifter;
    private double maxPower = 1.0;
    private AHRS gyro;

    public DriveTrain() {
        gyro = new AHRS(SerialPort.Port.kUSB);
        left = new DriveTrainSide(Side.LEFT);
        right = new DriveTrainSide(Side.RIGHT);
        comp = new Compressor();
        shifter = new Solenoid(RobotMap.SHIFTER_SOLENOID);
        comp.start();
        setBrakeMode();
   }

    public AHRS getGyro(){
        return gyro;
    }
    
    
    public double getGyroHeading(){
        return Math.toRadians(gyro.getAngle());
    }

    public void setPowerLeftRight(double lpow, double rpow) {
        left.setPower(lpow * maxPower);
        right.setPower(rpow * maxPower);
    }

    public void setMaxPower(double max) {
        maxPower = Math.min(1, Math.abs(max));
    }

    public void setPowerLeftRight(Vector2d vec2) {
        setPowerLeftRight(vec2.getX(), vec2.getY());
    }


    public void driveDistance(double dist) {
        left.driveDistance(dist);
        right.driveDistance(dist);
    }


    public DriveTrainSide getLeftSide() {
        return left;
    }

    public DriveTrainSide getRightSide() {
        return right;
    }

    public void resetEncs() {
        left.resetEnc();
        right.resetEnc();
    }

    public Vector2d getDistance() {
        return new Vector2d(left.getSidePosition(), right.getSidePosition());
    }

    public void setCoastMode() {
        left.setCoastMode();
        right.setCoastMode();
    }

    public void setBrakeMode() {
        left.setBrakeMode();
        right.setBrakeMode();
    }

    public void setPowerStraight(double numIn) {
        left.setPower(numIn);
        right.setPower(numIn);
    }

    public double getAvgSidePosition() {
        return (left.getSidePosition() + right.getSidePosition()) / 2;
    }

    public void stop() {
        left.setBrakeMode();
        right.setBrakeMode();
        this.setPowerLeftRight(0, 0);
    }

    public void shiftGears(Gear gear) {
        shifter.set(gear == Gear.LOW);
    }

    @Override
    protected void initDefaultCommand() {
        /*driving logic is here*/
        setDefaultCommand(
                new DriveControlCommand(Robot.runningRobot.driver.JS_FW_BACK, Robot.runningRobot.driver.JS_TURN));
    }

}
