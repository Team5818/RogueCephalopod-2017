package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorPower extends Command {

    CollectorRollers roll;

    public SetCollectorPower() {
        setTimeout(2);
        roll = Robot.runningRobot.roll;
    }

    @Override
    protected void initialize() {
        roll.setTopPower(.7);
        roll.setBotPower(.7);
    }

    @Override
    protected void end() {
        roll.setBotPower(0.0);
        roll.setTopPower(0.0);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

}
