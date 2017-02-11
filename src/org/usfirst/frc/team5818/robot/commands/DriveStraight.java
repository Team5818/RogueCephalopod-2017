package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Command {

    private double inches;
    private double maxPow;
    private double leftPowMult;
    private double rightPowMult;
    private double leftVel;
    private double rightVel;
    private double leftStart;
    private double rightStart;
    private double targetRatio;
    private boolean stopAtEnd;
    private int camMultiplier;
    private boolean useVision;
    private double maxRatio;
    
    public enum Camera{
        CAM_FORWARD, CAM_BACKWARD, NONE;
    }
    
    public DriveStraight(double in, double pow, double targetRat, double maxRat, Camera cam, boolean stop) {
        inches = in;
        maxPow = pow;
        requires(Robot.runningrobot.driveTrain);
        setTimeout(in / 12);
        targetRatio = targetRat; // Ratio is LEFT/RIGHT
        maxRatio = maxRat;
        
        if(cam.equals(Camera.NONE)){
            camMultiplier = 0;
            useVision = false;
        }
        else if(cam.equals(Camera.CAM_FORWARD)){
            camMultiplier = 1;
            useVision = true;
        }
        else if(cam.equals(Camera.CAM_BACKWARD)){
            camMultiplier = -1;
            useVision = true;
        }
        
        stopAtEnd = stop;
    }
    /**
     * No vision constructor
     */
    public DriveStraight(double in, double pow, double targetRatio, boolean stop) {
        this(in, pow, targetRatio, 1.0, Camera.NONE, stop);
    }
    /**
     * Vision Constructor
     */
    public DriveStraight(double in, double pow, double maxRatio, Camera cam, boolean stop) {
        this(in, pow, 1.0, maxRatio, cam, stop);
    }

    @Override
    public void initialize() {
        leftPowMult = 1;
        rightPowMult = 1;
        Driver.joystickControlEnabled = false;
        leftStart = Robot.runningrobot.driveTrain.left.getSidePosition();
        rightStart = Robot.runningrobot.driveTrain.left.getSidePosition();
    }

    public void execute() {
        leftVel =
                Math.abs(Robot.runningrobot.driveTrain.left.getSideVelocity());
        rightVel =
                Math.abs(Robot.runningrobot.driveTrain.right.getSideVelocity());
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }

        double anglePower = Robot.runningrobot.track.getCurrentAngle()/BotConstants.CAMERA_FOV*camMultiplier*2.0;
        
        double target = targetRatio;
        
        if(useVision){
            target = Math.pow(maxRatio, anglePower);
        }

        leftPowMult = 1.0;
        rightPowMult = currRatio / target;

        Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
        driveVec = driveVec.normalize(maxPow);

        Robot.runningrobot.driveTrain.setPowerLeftRight(driveVec);

        SmartDashboard.putString("DB/String 0",
                String.format("%.3f", leftVel / rightVel));
        SmartDashboard.putString("DB/String 1",
                String.format("%.3f", driveVec.getX()));
        SmartDashboard.putString("DB/String 2",
                String.format("%.3f", driveVec.getY()));
    }

    @Override
    public void end() {
        if (stopAtEnd) {
            Robot.runningrobot.driveTrain.stop();
        }
        Driver.joystickControlEnabled = true;
    }

    @Override
    public synchronized void cancel() {
        // TODO Auto-generated method stub
        super.cancel();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || Math
                .abs(Robot.runningrobot.driveTrain.left.getSidePosition())
                - Math.abs(leftStart) >= Math.abs(inches)
                && Math.abs(
                        Robot.runningrobot.driveTrain.right.getSidePosition())
                        - Math.abs(rightStart) >= Math.abs(inches);
    }

}
