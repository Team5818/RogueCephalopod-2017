package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.FastLoop;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Trajectory;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;

public class DrivePath implements FastLoop{

    private Trajectory trajectoryLeft;
    private Trajectory trajectoryRight;
    private RequireCommand require;
    private DriveTrain dt;
    private double dtx;
    private double dty;
    private double heading; 
    private double encDist;
    private int segNum;
    private static final double P_TURN = 300.0;
    
    public DrivePath(Trajectory trajL, Trajectory trajR) {
        trajectoryLeft = trajL;
        trajectoryRight = trajR;
        require = new RequireCommand();
        dt = Robot.runningRobot.driveTrain;
    }
    
    @Override
    public boolean isFinished() {
        return segNum > trajectoryLeft.getNumSegments();
    }

    @Override
    public void update() {
        /*try to keep track of robot's (x,y) position*/
        double newHead = dt.getGyroHeading();
        double newDist = dt.getAvgSidePosition();
        dtx = dtx + (newDist - encDist)*Math.sin(newHead - heading);
        dty = dty + (newDist - encDist)*Math.cos(newHead - heading);
        encDist = newDist;
        heading = newHead;
        
        /*take care of trajectories*/
        CANTalon.TrajectoryPoint pointLeft = new CANTalon.TrajectoryPoint();
        CANTalon.TrajectoryPoint pointRight = new CANTalon.TrajectoryPoint();
        pointLeft.position = trajectoryLeft.getSegment(segNum).pos;
        pointRight.position = trajectoryRight.getSegment(segNum).pos;
        double goalHeading = trajectoryLeft.getSegment(segNum).heading;
        double angleDiffRads = MathUtil.wrapAngleRad(goalHeading - dt.getGyroHeading());

        double turn =  P_TURN * angleDiffRads;        
        pointLeft.velocity = trajectoryLeft.getSegment(segNum).vel + turn;
        pointRight.velocity = trajectoryRight.getSegment(segNum).vel - turn;
        pointLeft.timeDurMs = (int) trajectoryLeft.getSegment(segNum).dt;
        pointRight.timeDurMs = (int) trajectoryRight.getSegment(segNum).dt;
        
        dt.getLeftSide().getMasterTalon().pushMotionProfileTrajectory(pointLeft);
        dt.getLeftSide().getMasterTalon().processMotionProfileBuffer();
        dt.getRightSide().getMasterTalon().pushMotionProfileTrajectory(pointRight);
        dt.getRightSide().getMasterTalon().processMotionProfileBuffer();
        segNum++;    
    }

    @Override
    public void done() {
        require.stop();
    }

    @Override
    public void start() {
        dt.resetEncs();
        dtx = 0.0;
        dty = 0.0;
        encDist = 0.0;
        segNum = 0;
        require.require(dt);
        heading = dt.getGyroHeading();
    }
    
}
