package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopVisionDriving extends Command {

    @Override
    protected void initialize() {
        Robot.runningRobot.driveTrain.disableVisionDriving();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
