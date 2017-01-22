package org.usfirst.frc.team1717.robot.commands;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DrivePIDDistance extends Command {
	private double inches;
	private DriveTrain dt = Robot.runningrobot.driveTrain;	
	
	public DrivePIDDistance(double inches) {
		requires(Robot.runningrobot.driveTrain);
		this.inches = inches;
	}
	
	public void initialize() {
		// dt.setCoastMode();
	}


	protected void execute() {
		dt.driveDistance(inches);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
}
