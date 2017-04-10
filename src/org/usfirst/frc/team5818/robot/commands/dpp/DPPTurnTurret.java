package org.usfirst.frc.team5818.robot.commands.dpp;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

class DPPTurnTurret extends Command {

    private final Turret turret = Robot.runningRobot.turret;

    private final int loopCount;

    private double startAngle;
    private double targetAngle;

    DPPTurnTurret(int loopCount) {
        requires(turret.rotator);
        this.loopCount = loopCount;
    }

    @Override
    protected void initialize() {
        final double angleSign;
        if (loopCount == 0) {
            angleSign = 1;
        } else /* if (loopCount == 1) */ {
            angleSign = -1;
        }
        startAngle = turret.getAngle();
        targetAngle = startAngle + (angleSign * 2.5 * (loopCount + 1));
        turret.setPower(angleSign * .3);
    }

    @Override
    protected void end() {
        turret.setPower(0);
    }

    @Override
    protected boolean isFinished() {
        double angle = turret.getAngle();
        boolean passedTarget1 = startAngle < targetAngle && targetAngle < angle;
        boolean passedTarget2 = angle < targetAngle && targetAngle < startAngle;
        return passedTarget1 || passedTarget2;
    }

}
