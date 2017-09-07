package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.command.Command;

public class MagicSpin extends Command{

    private static final double COUNTS_PER_ROTATION = 0.0;
    private DriveTrain dt;
    private double angle;
    private boolean onTarget;
    private int targetCounter;
    
    public MagicSpin(double ang) {
        dt = Robot.runningRobot.driveTrain;
        angle = ang;
    }
    
    @Override
    protected void initialize() {}
    
    @Override
    protected void execute() {
        double diff = MathUtil.wrapAngleRad(angle - dt.getGyroHeading());
        double dist = diff*COUNTS_PER_ROTATION;
        dt.getLeftSide().driveDistance(dist);
        dt.getLeftSide().driveDistance(-dist);
        onTarget = Math.abs(angle - dt.getGyroHeading()) < .01;
        if(onTarget){
            targetCounter++;
        }
        else {
            targetCounter = 0;
        }
    }
    
    @Override
    protected boolean isFinished() {
        return targetCounter > 10;
    }

    @Override
    protected void end() {
        dt.stop();
    } 
    
}
