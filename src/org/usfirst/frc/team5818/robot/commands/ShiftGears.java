package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGears extends Command {

    private DriveTrain train;
    private boolean gear;
    private static double SHIFT_TIME = .5;

    public ShiftGears(boolean g) {
        train = Robot.runningRobot.driveTrain;
        gear = g;
        setTimeout(SHIFT_TIME);
    }

    @Override
    protected void initialize() {
        train.shiftGears(gear);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

}
