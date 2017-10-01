package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the collector power to given parameters, with timeout.
 */
public class SetCollectorPower extends Command {

    private final double power;
    private final Collector collect = Robot.runningRobot.collect;

    public SetCollectorPower(boolean ejectForwards, double pow, double timeout) {
        requires(collect);
        setTimeout(timeout);
        power = ejectForwards ? pow : -pow;
    }

    public SetCollectorPower(boolean ejectForwards) {
        this(ejectForwards, 0.7, 1);
    }

    @Override
    protected void initialize() {
        collect.setTopPower(power);
        collect.setBotPower(power);
    }

    @Override
    protected void end() {
        collect.setBotPower(0.0);
        collect.setTopPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
