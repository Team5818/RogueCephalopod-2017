package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorAngle extends Command {

    public static final double TOLERANCE = 3;

    private Collector collector;
    private double targetAng;

    public SetCollectorAngle(double angle) {
        collector = Robot.runningrobot.collector;
        targetAng = angle;
        requires(collector);
    }

    @Override
    public void initialize() {
        collector.getanglePID().setAbsoluteTolerance(TOLERANCE);
        collector.getanglePID().setSetpoint(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return collector.getanglePID().onTarget();
    }

    @Override
    public void end() {
        collector.stop();
    }

}
