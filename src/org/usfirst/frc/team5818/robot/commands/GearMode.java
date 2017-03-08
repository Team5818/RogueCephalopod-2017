package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class GearMode extends Command {

    private CameraController cont;
    private VisionTracker vision;

    public GearMode() {
        cont = Robot.runningRobot.camCont;
        vision = Robot.runningRobot.vision;
        requires(cont);
        requires(vision);
    }

    @Override
    protected void initialize() {
        cont.enterGearMode();
        vision.setLightsOn(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
