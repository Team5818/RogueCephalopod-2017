package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class TurretSmallAdjustment extends Command {

    private Turret turr = Robot.runningRobot.turret;
    private double targetAngle;
    private double startAngle;

    public TurretSmallAdjustment(double ang) {
        targetAngle = ang;
    }

    @Override
    protected void initialize() {
        startAngle = turr.getAngle();
    }

    @Override
    protected void execute() {
        turr.setPower(.3 * Math.signum(startAngle - targetAngle));
    }

    @Override
    protected boolean isFinished() {
        if (startAngle < targetAngle) {
            return turr.getAngle() >= targetAngle - .5;
        } else {
            return turr.getAngle() <= targetAngle + .5;
        }
    }

    @Override
    protected void end() {
        turr.setPower(0.0);
    }

}
