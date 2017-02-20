package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DrivePIDDistance extends Command {

    public static final int DEFAULT_TIMEOUT = 5;

    private double inches;
    private DriveTrain dt = Robot.runningRobot.driveTrain;

    public DrivePIDDistance(double inches, double timeout) {
        requires(Robot.runningRobot.driveTrain);
        this.inches = inches;
        setTimeout(timeout);
    }

    public DrivePIDDistance(double inches) {
        this(inches, DEFAULT_TIMEOUT);
    }

    @Override
    public void initialize() {
        dt.driveDistance(inches);
    }

    @Override
    protected boolean isFinished() {
        return (dt.getLeftSide().getDistController().onTarget() && dt.getRightSide().getDistController().onTarget())
                || isTimedOut();
    }

    @Override
    protected void end() {
        dt.stop();
    }
}
