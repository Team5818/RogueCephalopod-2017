package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;

import edu.wpi.first.wpilibj.command.Command;

public class ExposureLow extends Command {

    private CameraController cont;

    public ExposureLow() {
        cont = Robot.runningrobot.camCont;
        requires(cont);
    }

    @Override
    protected void initialize() {
        cont.exposureLow();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
