package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class MagicDrive extends Command {

    private DriveTrain dt;
    private double distance;

    public MagicDrive(double dist) {
        distance = dist;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(10);
    }

    @Override
    public void initialize() {
        dt.driveDistance(distance);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(dt.getAvgSidePosition() - distance) < .2 && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2;
    }

    @Override
    protected void end() {
        dt.stop();
    }

}
