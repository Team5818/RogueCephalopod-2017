package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.commands.DriveControlCommand;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

    public DriveTrainSide left;
    public DriveTrainSide right;
    private Ultrasonic sanic;

    public DriveTrain() {
        left = new DriveTrainSide(Side.LEFT);
        right = new DriveTrainSide(Side.RIGHT);
        sanic = new Ultrasonic(0, 1);
        enableSanic();
    }

    public Ultrasonic getSanic() {
        return sanic;
    }

    public void enableSanic() {
        sanic.setAutomaticMode(true);
    }

    public double readSanic() {
        return sanic.getRangeInches();
    }

    public String askSanic() {
        int idx = (int) Math.random() * 4;
        switch (idx) {
            case 0:
                return "Gotta go fast!";
            case 1:
                return "Sanic's my name!";
            case 2:
                return "Speed's my game!";
            case 3:
                return "Stop hating on sanic!";
            default:
                return "You're too slooooowww!!";
        }
    }

    public void setPowerLeftRight(double lpow, double rpow) {
        left.setPower(lpow);
        right.setPower(rpow);
    }

    public void setPowerLeftRight(Vector2d vec2) {
        setPowerLeftRight(vec2.getX(), vec2.getY());
    }

    public void setVelocityLeftRight(double lvel, double rvel) {
        left.driveVelocity(lvel);
        right.driveVelocity(rvel);
    }

    public void setVelocityLeftRight(Vector2d vec2) {
        setVelocityLeftRight(vec2.getX(), vec2.getY());
    }

    public void driveDistance(double dist) {
        left.driveDistance(dist);
        right.driveDistance(dist);
    }

    public void setPIDSourceType(PIDSourceType type) {
        left.setPIDSourceType(type);
        right.setPIDSourceType(type);
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

    public double getAverageDistance() {
        return (left.getSidePosition() + right.getSidePosition()) / 2;
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

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveControlCommand());
    }

}
