package org.usfirst.frc.team1717.robot.commands;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DrivePIDDistance extends Command {
	private double inches;
	private DriveTrain dt = Robot.runningrobot.driveTrain;	
	public static final int DEFAULT_TIMEOUT = 5;
	
	public DrivePIDDistance(double inches, double timeout) {
		requires(Robot.runningrobot.driveTrain);
		this.inches = inches;
		setTimeout(timeout);
	}
	
	public DrivePIDDistance(double inches){
		this(inches, DEFAULT_TIMEOUT);
	}
	
	public void initialize() {
		dt.driveDistance(inches);
	}


	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return (dt.getLeftSide().getDistController().onTarget() 
				&& dt.getRightSide().getDistController().onTarget()) || isTimedOut();
	}
	
	@Override
	protected void end(){
		dt.stop();
	}
	
	@Override
	protected void interrupted(){
		end();
	}
}
