package org.usfirst.frc.team5818.robot.commands;

import java.util.Iterator;
import java.util.List;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Lidar;

import edu.wpi.first.wpilibj.command.Command;
import io.scanse.sweep.SweepSample;

public final class SweepReader extends Command {

    private final Lidar lidar = Robot.runningRobot.lidar;
    private Iterator<List<SweepSample>> scanIter;

    public SweepReader() {
        requires(lidar);
    }

    @Override
    protected void initialize() {
        scanIter = lidar.getDevice().scans().iterator();
    }

    @Override
    protected void execute() {
        List<SweepSample> next = scanIter.next();
        lidar.setCurrentScan(next);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}