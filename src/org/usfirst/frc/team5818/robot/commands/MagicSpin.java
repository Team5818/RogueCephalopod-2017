package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagicSpin extends Command{

    private static final double INCHES_PER_ROTATION = 100.0;
    private DriveTrain dt;
    private double angle;
    private boolean onTarget;
    private int targetCounter;
    
    public MagicSpin(double ang) {
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        angle = ang;
    }
    
    @Override
    protected void initialize(){
    	dt.getLeftSide().positionControl();
    	dt.getRightSide().positionControl();
    	dt.resetEncs();
    }
    
    @Override
    protected void execute() {
    	double diff = MathUtil.wrapAngleRad(angle - dt.getGyroHeading());
    	DriverStation.reportError("" + diff, false);
    	double dist = diff/(2.0 * Math.PI) * INCHES_PER_ROTATION;
    	SmartDashboard.putNumber("spin dist", dist);
    	DriverStation.reportError("" + dist, false);
    	dt.getLeftSide().driveDistanceNoReset(dt.getLeftSide().getSidePosition() + dist);
    	dt.getRightSide().driveDistanceNoReset(dt.getRightSide().getSidePosition()-dist);
    }
    
    
    @Override
    protected boolean isFinished() {
        return Math.abs(MathUtil.wrapAngleRad(angle - dt.getGyroHeading())) < .03 && dt.getLeftSide().getSideVelocity() < 2 && dt.getLeftSide().getSideVelocity() < 2;
    }

    @Override
    protected void end() {
        dt.stop();
    } 
    
}
