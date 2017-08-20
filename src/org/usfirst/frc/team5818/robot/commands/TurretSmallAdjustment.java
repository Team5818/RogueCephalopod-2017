package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class TurretSmallAdjustment extends Command {

    private Turret turr = Robot.runningRobot.turret;
    private double targetPosition;
    private double startAngle;

    public TurretSmallAdjustment(double ang) {
        setTimeout(0.5);
        requires(turr.rotator);
        targetPosition = ang;
    }

    @Override
    protected void initialize() {
        startAngle = turr.getPosition();
    }

    @Override
    protected void execute() {
        turr.setPower(.3 * Math.signum(targetPosition - startAngle));
    }

    @Override
    protected boolean isFinished() {
        boolean passedTarget1 = startAngle <= targetPosition && (targetPosition - .3) <= turr.getPosition();
        boolean passedTarget2 = targetPosition <= startAngle && turr.getPosition() <= (targetPosition + .3);
        return passedTarget1 || passedTarget2 || isTimedOut();
    }

    @Override
    protected void end() {
        turr.setPower(0.0);
    }

}
