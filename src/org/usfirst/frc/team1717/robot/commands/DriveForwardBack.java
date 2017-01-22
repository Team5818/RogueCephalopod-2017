package org.usfirst.frc.team1717.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardBack extends CommandGroup {
	
	private DrivePowerDistance driveForward;
	private DrivePowerDistance driveBackward;
	
	public DriveForwardBack(double power, double inches, double timeout) {
		setTimeout(timeout);
		driveForward = new DrivePowerDistance(power, inches, timeout/2);
		driveBackward = new DrivePowerDistance(-power, inches, timeout/2);
		this.addSequential(driveForward);
		this.addSequential(driveBackward);
	}

}
