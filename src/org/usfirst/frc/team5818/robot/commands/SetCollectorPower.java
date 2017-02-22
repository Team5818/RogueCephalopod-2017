package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorPower extends Command {

    private final double power;
    private final CollectorRollers roll = Robot.runningRobot.roll;

    public SetCollectorPower(boolean ejectForwards) {
        setTimeout(1);
        power = ejectForwards ? 0.7 : -0.7;
    }

    @Override
    protected void initialize() {
        roll.setTopPower(power);
        roll.setBotPower(power);
    }

    @Override
    protected void end() {
        roll.setBotPower(0.0);
        roll.setTopPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
