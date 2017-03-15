package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class TurretSmallAdjustment extends Command {

    private Turret turr = Robot.runningRobot.turret;
    private double targetAngle;
    private double startAngle;

    public TurretSmallAdjustment(double ang) {
        setTimeout(0.5);
        requires(turr);
        targetAngle = ang;
    }

    @Override
    protected void initialize() {
        startAngle = turr.getAngle();
    }

    @Override
    protected void execute() {
        turr.setPower(.3 * Math.signum(targetAngle - startAngle));
    }

    @Override
    protected boolean isFinished() {
        boolean passedTarget1 = startAngle <= targetAngle && (targetAngle - .3) <= turr.getAngle();
        boolean passedTarget2 = targetAngle <= startAngle && turr.getAngle() <= (targetAngle + .3);
        return passedTarget1 || passedTarget2 || isTimedOut();
    }

    @Override
    protected void end() {
        turr.setPower(0.0);
    }

}
