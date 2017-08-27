package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class SetTurretAngle extends Command {

    public static final double DEGREES_TOLERANCE = 2;

    private Turret turr;
    private double targetAng;

    public SetTurretAngle(double ang) {
        setInterruptible(false);
        turr = Robot.runningRobot.turret;
        requires(turr.rotator);
        setTimeout(0.5);
        targetAng = ang;
    }

    @Override
    public void initialize() {
        turr.setAngle(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

}
