package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class SetExtendMini extends Command {

    private Turret turr;
    private boolean on;

    public SetExtendMini(boolean b) {
        turr = Robot.runningRobot.turret;
        requires(turr);
        on = b;
    }

    @Override
    protected void initialize() {
        turr.currentMini(on);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
