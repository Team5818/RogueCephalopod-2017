package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Constants;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Trajectory;
import org.usfirst.frc.team5818.robot.utils.TrajectoryFollower;
import org.usfirst.frc.team5818.robot.utils.TrajectoryGenerator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SpinWithProfile.java This controller spins the drivetrain
 * 
 * @author jproney
 */
public class SpinWithProfileVision extends Command {

    private static final double minDeltaHeading = 2;

    private DriveTrain driveTrain;
    private TrajectoryFollower followerLeft;
    private TrajectoryFollower followerRight;
    private boolean aligned = false;
    private int alignedCounter = 0;
    private VisionTracker vision;
    boolean stoppingAtEnd;
    double angle;
    double kTurn = 1.5/Math.PI;
    double kSmall = 3.5/Math.PI;
    int loopCount;
    int camMultiplier;

    public SpinWithProfileVision(boolean stop, Camera cam) {
        setTimeout(1.5);
        if(cam.equals(Camera.CAM_TAPE)){
            camMultiplier = 1;
        }else{
            camMultiplier = -1;
        }
        
        driveTrain = Robot.runningRobot.driveTrain;
        vision = Robot.runningRobot.vision;
        requires(driveTrain);
        stoppingAtEnd = stop;
    }

    protected void initialize() {

    }

//    public void reset() {
//        followerLeft.reset();
//        followerRight.reset();
//        driveTrain.resetEncs();
//    }
//
//    public int getFollowerCurrentSegment() {
//        return followerLeft.getCurrentSegment();
//    }
//
//    public int getNumSegments() {
//        return followerLeft.getNumSegments();
//    }

    protected void execute() {
        double turn = 0;
        double visAng = vision.getCurrentAngle();
            if(!Double.isNaN(visAng)){
                turn = kSmall*Math.toRadians(vision.getCurrentAngle())*camMultiplier;
            }
        driveTrain.setPowerLeftRight( -turn, + turn);
        
        aligned = Math.abs(vision.getCurrentAngle()) < 1;
        
        if(aligned){
            alignedCounter++;
        }
        else{
            alignedCounter = 0;
        }
    }

    @Override
    protected boolean isFinished() {
        return alignedCounter > 3 || isTimedOut();
    }

    @Override
    protected void end() {
        if (stoppingAtEnd) {
            driveTrain.stop();
        }
    }
}
