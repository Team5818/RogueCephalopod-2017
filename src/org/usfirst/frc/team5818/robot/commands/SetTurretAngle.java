package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class SetTurretAngle extends Command {

    public static final double DEGREES_TOLERANCE = 2;

    private Turret turr;
    private double targetAng;

    public SetTurretAngle(double ang) {
        turr = Robot.runningrobot.turret;
        requires(turr);
        targetAng = ang;
    }

    @Override
    public void initialize() {
        turr.getAngController().setAbsoluteTolerance(DEGREES_TOLERANCE);
        turr.setAng(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return turr.getAngController().onTarget();
    }

    @Override
    protected void end() {
        turr.getAngController().disable();
    }

}
