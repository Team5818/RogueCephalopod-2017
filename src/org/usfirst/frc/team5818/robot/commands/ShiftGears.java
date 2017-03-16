package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGears extends Command {

    private DriveTrain train;
    private Gear gear;
    private static double SHIFT_TIME = .5;

    public ShiftGears(Gear g) {
        this(g, SHIFT_TIME);
    }

    public ShiftGears(Gear g, double timeout) {
        train = Robot.runningRobot.driveTrain;
        gear = g;
        setTimeout(timeout);
    }

    @Override
    protected void initialize() {
        train.shiftGears(gear);
        train.setMaxPower(.5);

    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

    protected void end() {
        train.setMaxPower(1.0);
    }

}
