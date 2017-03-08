package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class SetPunchTurret extends Command {

    private Turret turr;
    private boolean on;

    public SetPunchTurret(boolean b) {
        turr = Robot.runningRobot.turret;
        requires(turr);
        on = b;
    }

    @Override
    protected void initialize() {
        turr.punch(on);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}