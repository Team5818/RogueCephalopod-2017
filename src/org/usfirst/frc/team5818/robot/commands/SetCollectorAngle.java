package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorAngle extends Command {

    private Collector collector;
    double targetAng;
    public static final double TOLERANCE = 3;

    public SetCollectorAngle(double angle) {
        collector = Robot.runningrobot.collector;
        targetAng = angle;
    }

    public void initialize() {
        collector.getanglePID().setAbsoluteTolerance(TOLERANCE);
        collector.getanglePID().setSetpoint(targetAng);
    }

    @Override
    public void execute() {

    }

    @Override
    protected boolean isFinished() {
        return collector.getanglePID().onTarget();
    }

    @Override
    public void end() {
        collector.stop();
    }

    @Override
    public void interrupted() {
        collector.stop();
    }
}
