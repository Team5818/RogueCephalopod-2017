package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PutGearInTurret extends CommandGroup {
	CollectorRollers roll = Robot.runningRobot.roll;
	public PutGearInTurret() {
		this.addSequential(new SetTurretAngle(0));
		this.addSequential(new SetCollectorAngle(Collector.LOAD_POSITION));
		this.addSequential(new SetCollectorPower(false, 0.7, 1000));
		requires(Robot.runningRobot.roll);
		setInterruptible(true);
	}
	@Override
	protected void end() {
		roll.setBotPower(0);
		roll.setTopPower(0);
	}
}
