package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.controllers.Driver;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command {

    private Driver driver;

    public SwitchDriveMode() {
        driver = Robot.runningRobot.driver;
    }

    @Override
    protected void initialize() {
        switch (driver.dMode) {
            case POWER:
                driver.dMode = DriveMode.VELOCITY;
                break;
            default /* case VELOCITY */:
                driver.dMode = DriveMode.POWER;
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
