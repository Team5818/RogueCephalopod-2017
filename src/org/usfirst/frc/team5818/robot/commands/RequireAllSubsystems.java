package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RequireAllSubsystems extends Command {

    public RequireAllSubsystems() {
        requires(Robot.runningRobot.driveTrain);
        requires(Robot.runningRobot.climb);
        requires(Robot.runningRobot.collector);
        requires(Robot.runningRobot.roll);
        requires(Robot.runningRobot.turret);
        setInterruptible(false);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
