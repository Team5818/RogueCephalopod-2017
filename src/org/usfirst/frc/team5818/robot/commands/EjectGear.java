package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class EjectGear extends TimedCommand {

    private static final double DEFAULT_DURATION = 1;
    private final double power;
    private final CollectorRollers collectorRollers = Robot.runningRobot.roll;

    public EjectGear(double pow) {
        super(DEFAULT_DURATION);
        this.power = pow;
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
    protected void end() {
        collectorRollers.setTopPower(0);
        collectorRollers.setBotPower(0);
    }

}
