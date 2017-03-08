package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class CollectGearCurrent extends Command {

    private static final double DEFAULT_CURRENT_THRESH = 6.0;
    private double currThresh;
    private double power;
    private final CollectorRollers collectorRollers = Robot.runningRobot.roll;

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
        collectorRollers.setTopPower(power);
        collectorRollers.setBotPower(power);
    }

    @Override
    protected void execute() {
        collectorRollers.setTopPower(power);
        collectorRollers.setBotPower(power);
    }

    @Override
    protected boolean isFinished() {
        return collectorRollers.getBotCurrent() > currThresh || isTimedOut();
    }

}
