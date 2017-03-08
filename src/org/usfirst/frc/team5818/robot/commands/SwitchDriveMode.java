package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchDriveMode extends Command {

    private Driver driver;
    private DriveCalculator driveCalc;

    public SwitchDriveMode(DriveCalculator calc) {
        driver = Robot.runningRobot.driver;
        driveCalc = calc;
    }

    @Override
    protected void initialize() {
        driver.driveCalc = driveCalc;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
