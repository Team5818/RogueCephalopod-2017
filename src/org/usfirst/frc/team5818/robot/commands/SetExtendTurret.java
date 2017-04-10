package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetExtendTurret extends InstantCommand {

    private Turret turr;
    private boolean on;

    public SetExtendTurret(boolean b) {
        turr = Robot.runningRobot.turret;
        requires(turr.deployer);
        on = b;
    }

    @Override
    protected void initialize() {
        turr.extend(on);
    }

}
