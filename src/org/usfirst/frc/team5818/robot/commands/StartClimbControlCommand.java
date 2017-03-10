package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class StartClimbControlCommand extends Command {
    
    @Override
    protected void execute() {
        Robot.runningRobot.turretSafetyChecks = false;
        Robot.runningRobot.turretZero.cancel();
        new ClimbControlCommand(Robot.runningRobot.driver.JS_TURRET).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
