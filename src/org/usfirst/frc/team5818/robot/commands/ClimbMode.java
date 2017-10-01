package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Routine that puts the robot into climb mode. This cannot exit after it is
 * activated.
 */
public class ClimbMode extends CommandGroup {

	public ClimbMode() {
		setInterruptible(false);
		addSequential(new SetClimbArmLimit());
		addParallel(new ClimbControlCommand(Robot.runningRobot.driver.JS_TURRET));
		addParallel(new ArmControlCommand(Robot.runningRobot.driver.JS_COLLECTOR));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
