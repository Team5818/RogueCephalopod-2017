package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorAngle extends Command {

    public static final double TOLERANCE = 50;

    private Collector collector;
    private double targetAng;

    public SetCollectorAngle(double angle) {
        collector = Robot.runningRobot.collector;
        targetAng = angle;
        requires(collector);
    }

    @Override
    public void initialize() {
        collector.setBrakeMode(false);
        collector.getAnglePID().setAbsoluteTolerance(TOLERANCE);
        collector.getAnglePID().setToleranceBuffer(2);
        collector.setAngle(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return collector.getAnglePID().onTarget();
    }

    @Override
    public void end() {
        collector.stop();
        collector.setBrakeMode(true);
    }

}
