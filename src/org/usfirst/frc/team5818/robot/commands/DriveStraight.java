package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command{

    private static final double P_TURN = 400.0; 
    private DriveTrain dt;
    private double distance;
    private double heading;
    private double baselineVel;
    private boolean sidesLocked = false;
    
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
            double turnPower = P_TURN*MathUtil.wrapAngleRad(heading - dt.getGyroHeading());
            dt.getLeftSide().setCruiseVel(baselineVel + turnPower);
            dt.getRightSide().setCruiseVel(baselineVel - turnPower);
        } else {
            dt.getLeftSide().setCruiseVel(baselineVel);
            dt.getRightSide().setCruiseVel(baselineVel);
            dt.getLeftSide().setAccel(800);
            dt.getRightSide().setAccel(800);
            dt.getRightSide().enslaveToOtherSide(true);
        }
    }
    
    
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
    
    @Override
    protected void end(){
        dt.getLeftSide().configTalons();
        dt.getRightSide().configTalons();
        dt.stop();
    }

}
