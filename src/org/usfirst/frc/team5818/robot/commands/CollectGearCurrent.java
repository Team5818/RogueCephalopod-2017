package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class CollectGearCurrent extends Command {

    private static final double DEFAULT_CURRENT_THRESH = 3.5;
    private double currThresh;
    private double power;
    private final CollectorRollers collectorRollers = Robot.runningRobot.roll;

    public CollectGearCurrent(double thresh, double pow) {
        currThresh = thresh;
        power = pow;
    }

    public CollectGearCurrent(double pow) {
        this(DEFAULT_CURRENT_THRESH, pow);
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
        return collectorRollers.getTopCurrent() > currThresh;
    }

    @Override
    protected void end() {
    }
}
