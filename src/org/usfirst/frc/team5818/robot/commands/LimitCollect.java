package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class LimitCollect extends Command {

    private double power;
    private final Collector collect = Robot.runningRobot.collect;

    public LimitCollect(double pow, double timeout) {
        power = pow;
        setTimeout(timeout);
        requires(collect);
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
        return collect.isLimitTriggered() || isTimedOut();
    }
}
