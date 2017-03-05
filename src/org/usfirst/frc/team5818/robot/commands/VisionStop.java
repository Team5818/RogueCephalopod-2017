package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionStop extends Command {

    public VisionStop() {
    }

    @Override
    protected void initialize() {
        Robot.runningRobot.camCont.enterTapeMode();
        Robot.runningRobot.driveTrain.enableVisionDriving(10);
        Robot.runningRobot.track.setLightsOn(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
