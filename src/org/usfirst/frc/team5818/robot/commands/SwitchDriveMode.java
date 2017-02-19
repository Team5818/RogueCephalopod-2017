package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.controllers.Driver;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command {

    Driver driver;
    private boolean isDone = false;

    public SwitchDriveMode() {
        driver = Robot.runningrobot.driver;
    }

    @Override
    protected void initialize() {
        if (driver.dMode.equals(DriveMode.POWER)) {
            driver.dMode = DriveMode.VELOCITY;
        } else if (driver.dMode.equals(DriveMode.VELOCITY)) {
            driver.dMode = DriveMode.POWER;
        }
        isDone = true;
    }

    @Override
    protected boolean isFinished() {
        return isDone;
    }

}
