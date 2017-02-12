package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.command.Command;

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


    public DriveStraight(double in, double pow, double targetRat, double maxRat, boolean vis, boolean stop) {
        inches = in;
        maxPow = pow;
        requires(Robot.runningrobot.driveTrain);
        setTimeout(in / 12);
        targetRatio = targetRat; // Ratio is LEFT/RIGHT
        maxRatio = maxRat;
        useVision = vis;
        camMultiplier = 0;
        if (useVision) {
            if(Robot.runningrobot.camCont.isFront()){
                camMultiplier = -1;
                maxPow = -Math.abs(maxPow);
            } else{
                camMultiplier = 1;
                maxPow = Math.abs(maxPow);
            }
        } 
        stopAtEnd = stop;
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

        double anglePower = Robot.runningrobot.track.getCurrentAngle()
                / BotConstants.CAMERA_FOV * camMultiplier * 2.0;

        double target = targetRatio;

        if (useVision) {
            target = Math.pow(maxRatio, anglePower);
        }

        leftPowMult = 1.0;
        rightPowMult = currRatio / target;

        Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
        driveVec = driveVec.normalize(maxPow);

        Robot.runningrobot.driveTrain.setPowerLeftRight(driveVec);

    }

    @Override
    public void end() {
        if (stopAtEnd) {
            Robot.runningrobot.driveTrain.stop();
        }
        Driver.joystickControlEnabled = true;
    }

    @Override
    protected boolean isFinished() {
        boolean passedTarget =
                Math.abs(Robot.runningrobot.driveTrain.left.getSidePosition())
                        - Math.abs(leftStart) >= Math.abs(inches)
                        && Math
                                .abs(Robot.runningrobot.driveTrain.right
                                        .getSidePosition())
                                - Math.abs(rightStart) >= Math.abs(inches);

        return isTimedOut() || passedTarget;

    }

}
