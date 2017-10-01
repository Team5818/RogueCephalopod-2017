package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Requires all subsystems in the robot so they're not doing anything. Used
 * during testing.
 */
public class RequireAllSubsystems extends Command {

	public RequireAllSubsystems() {
		requires(Robot.runningRobot.driveTrain);
		requires(Robot.runningRobot.climb);
		requires(Robot.runningRobot.arm);
		requires(Robot.runningRobot.collect);
		requires(Robot.runningRobot.turret.rotator);
		requires(Robot.runningRobot.turret.deployer);
		setInterruptible(false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
