package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class TapeMode extends Command {

    private CameraController cont;
    private VisionTracker vision;

    public TapeMode() {
        cont = Robot.runningRobot.camCont;
        vision = Robot.runningRobot.vision;
        requires(cont);
        requires(vision);
    }

    @Override
    protected void initialize() {
        cont.enterTapeMode();
        vision.setLightsOn(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
