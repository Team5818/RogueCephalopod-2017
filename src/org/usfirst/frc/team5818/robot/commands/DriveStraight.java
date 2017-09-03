package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command{

    private static final double P_TURN = 300.0; 
    private DriveTrain dt;
    private double distance;
    private double heading;
    private double baselineVel;
    
    public DriveStraight(double dist) {
        distance = dist;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(10);
    }
    
    @Override
    public void initialize() {
        dt.driveDistance(distance);
        heading = dt.getGyroHeading();
        baselineVel = dt.getLeftSide().getCruiseVel();
    }
    
    @Override
    protected void execute(){
        if(dt.getAvgSidePosition() < 1.0*distance) {
            double turnPower = P_TURN*(heading - dt.getGyroHeading());
            dt.getLeftSide().setCruiseVel(baselineVel + turnPower);
            dt.getRightSide().setCruiseVel(baselineVel - turnPower);
        }
        else{
            dt.getLeftSide().setCruiseVel(baselineVel);
            dt.getRightSide().setCruiseVel(baselineVel);
        }
    }
    
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut() || dt.getAvgSidePosition() > (distance - .5);
    }
    
    @Override
    protected void end(){
        dt.stop();
    }

}
