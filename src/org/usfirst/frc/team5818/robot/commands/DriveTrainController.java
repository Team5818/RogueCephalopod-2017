package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.FastLoop;
import org.usfirst.frc.team5818.robot.utils.Trajectory;

public class DriveTrainController implements FastLoop{

    private Trajectory tajectory;
    private DriveTrain dt;
    private double dtx;
    private double dty;
    private double heading; 
    private double encDist;
    private boolean runningProfile;
    
    public DriveTrainController(Trajectory traj) {
        tajectory = traj;
        dt = Robot.runningRobot.driveTrain;
    }
    
    @Override
    public boolean isFinished() {
        return false;
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
        if(runningProfile) {
            
        }
    }

    @Override
    public void done() {}

    @Override
    public void start() {
        dt.resetEncs();
        dtx = 0.0;
        dty = 0.0;
        encDist = 0.0;
        heading = dt.getGyroHeading();
    }
    
}
