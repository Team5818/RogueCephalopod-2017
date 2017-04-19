package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
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
public class SpinUntilTargetInFOV extends Command {

    private static final double minDeltaHeading = 2;

    private DriveTrain driveTrain;
    private VisionTracker vision;
    private double goalHeading;
    private TrajectoryFollower followerLeft;
    private TrajectoryFollower followerRight;
    boolean stoppingAtEnd;
    boolean fieldCentered;
    double angle;
    double kTurn = 1.5/Math.PI;
    double kSmall = .5/Math.PI;
    int loopCount;

    public SpinUntilTargetInFOV(double ang) {
        driveTrain = Robot.runningRobot.driveTrain;
        vision = Robot.runningRobot.vision;
        requires(driveTrain);
        angle = ang;
        fieldCentered = true;
        stoppingAtEnd = true;
    }

    protected void initialize() {
        loopCount = 0;
        double curHeading = driveTrain.getGyroHeading();
        if(fieldCentered){
            goalHeading = angle;
        }
        else{
            goalHeading = angle + curHeading;
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
        
        double turn;
        if(followerLeft.isFinishedTrajectory()){
            turn = kSmall * angleDiffRads;        
        }
        else{
            turn = kTurn * angleDiffRads;  
        }
        driveTrain.setPowerLeftRight(speedLeft - turn, speedRight + turn);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(angle - driveTrain.getGyroHeading()) < .05 || !Double.isNaN(vision.getCurrentAngle());
    }

    @Override
    protected void end() {
        if (stoppingAtEnd) {
            driveTrain.stop();
        }
    }
}