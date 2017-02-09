package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class SpinRobot extends Command{
	
	private DriveTrain train;
	private double targetAng;
	public static final double DEGREES_TOLERANCE = 1;
	
	public SetTurretAngle(double ang){
		train = Robot.runningrobot.driveTrain;
		targetAng = ang;
	}
	
	public void initialize(){
		train.getSpinController().setAbsoluteTolerance(DEGREES_TOLERANCE);
		train.spinAngle(targetAng);
	}
	
	@Override
	protected void execute(){
	}
	
	@Override
	protected boolean isFinished() {
		return train.getSpinController().onTarget();
	}
	
	@Override
	protected void end(){
		train.getSpinController().disable();
	}
	
	@Override
	protected void interrupted(){
		end();
	}

}