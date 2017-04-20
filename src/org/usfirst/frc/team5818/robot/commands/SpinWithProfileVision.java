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
    double kSmall = .3/Math.PI;
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
        loopCount = 0;
        double curHeading = driveTrain.getGyroHeading();
        double visAngle = vision.getCurrentAngle();
        double goalHeading = curHeading;
        if(!Double.isNaN(visAngle)){
            goalHeading = driveTrain.getGyroHeading() - camMultiplier*Math.toRadians(vision.getCurrentAngle())*.95;
        }
        double deltaHeading = goalHeading - curHeading;
        double distance = Math.abs(deltaHeading * Constants.Constant.wheelToWheelWidth()/2);

        Trajectory leftProfile = TrajectoryGenerator.generate(.2 * Constants.Constant.maxVelocityIPS(),
                .1 * Constants.Constant.maxAccelIPS2(), .02, 0.0, curHeading, Math.abs(distance), 0.0,
                goalHeading);
        Trajectory rightProfile = leftProfile.copy();

        if (Math.abs(deltaHeading) > Math.toRadians(minDeltaHeading)) {
            if (deltaHeading > 0) {
                leftProfile.scale(-1.0);
                rightProfile.scale(1.0);
            } else {
                leftProfile.scale(1.0);
                rightProfile.scale(-1.0);
            }
        }

        followerLeft = new TrajectoryFollower(.06, 1.0 / Constants.Constant.maxVelocityIPS(),
                0.3 / Constants.Constant.maxAccelIPS2(), leftProfile, Side.LEFT);
        followerRight = new TrajectoryFollower(.06, 1.0 / Constants.Constant.maxVelocityIPS(),
                0.3 / Constants.Constant.maxAccelIPS2(), rightProfile, Side.RIGHT);

        reset();
    }

    public void reset() {
        followerLeft.reset();
        followerRight.reset();
        driveTrain.resetEncs();
    }

    public int getFollowerCurrentSegment() {
        return followerLeft.getCurrentSegment();
    }

    public int getNumSegments() {
        return followerLeft.getNumSegments();
    }

    protected void execute() {
        loopCount++;
        double distanceL = driveTrain.getLeftSide().getSidePosition();
        double distanceR = driveTrain.getRightSide().getSidePosition();

        double speedLeft = followerLeft.calculate(-distanceL);
        double speedRight = followerRight.calculate(-distanceR);

        double goalHeading = followerLeft.getHeading();
        double observedHeading = driveTrain.getGyroHeading();

        double angleDiffRads = MathUtil.wrapAngleRad(goalHeading - observedHeading);
        
        
        double turn = 0;
        if(followerLeft.isFinishedTrajectory()){
            double visAng = vision.getCurrentAngle();
            if(!Double.isNaN(visAng)){
                turn = kSmall*Math.toRadians(vision.getCurrentAngle())*camMultiplier; 
            }
        }
        else{
            turn = kTurn * angleDiffRads;  
        }
        driveTrain.setPowerLeftRight(speedLeft - turn, speedRight + turn);
        
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
        return alignedCounter > 3;
    }

    @Override
    protected void end() {
        if (stoppingAtEnd) {
            driveTrain.stop();
        }
    }
}
