package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;

import edu.wpi.first.wpilibj.command.Command;

public class ShutDownRPi extends Command {

    private CameraController cont;

    public ShutDownRPi() {
        cont = Robot.runningrobot.camCont;
        requires(cont);
    }

    @Override
    protected void initialize() {
        cont.shutDown();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
