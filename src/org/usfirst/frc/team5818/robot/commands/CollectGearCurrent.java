package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class CollectGearCurrent extends Command {

    private static final double DEFAULT_CURRENT_THRESH = 5.5;
    private double currThresh;
    private double power;
    private final Collector collect = Robot.runningRobot.collect;

    public CollectGearCurrent(double thresh, double pow, double timeout) {
        setTimeout(timeout);
        currThresh = thresh;
        power = pow;
    }

    public CollectGearCurrent(double pow, double timeout) {
        this(DEFAULT_CURRENT_THRESH, pow, timeout);
    }

    @Override
    protected void initialize() {
        collect.setTopPower(power);
        collect.setBotPower(power);
    }

    @Override
    protected void execute() {
        collect.setTopPower(power);
        collect.setBotPower(power);
    }

    @Override
    protected boolean isFinished() {
        return collect.getBotCurrent() > currThresh || isTimedOut();
    }

}
