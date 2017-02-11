package org.usfirst.frc.team5818.robot.commands.collroll;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class LowPowerRoll extends TimedCommand {

    private static final double POWER = 0.1;

    private final CollectorRollers collectorRollers = Robot.runningrobot.collectorRollers;

    public LowPowerRoll() {
        super(1);
    }

    @Override
    protected void initialize() {
        collectorRollers.setTopPower(POWER);
        collectorRollers.setBotPower(POWER);
    }

    @Override
    protected void end() {
        collectorRollers.setTopPower(0);
        collectorRollers.setBotPower(0);
    }

}
