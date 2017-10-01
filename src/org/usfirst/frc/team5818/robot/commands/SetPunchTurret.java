package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Punches/un-punches the turret.
 */
public class SetPunchTurret extends Command {

    private Turret turr;
    private boolean on;

    public SetPunchTurret(boolean b, double timeout) {
        turr = Robot.runningRobot.turret;
        requires(turr.deployer);
        on = b;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        turr.punch(on);
    }

    @Override
    protected void execute() {
        turr.punch(on);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
