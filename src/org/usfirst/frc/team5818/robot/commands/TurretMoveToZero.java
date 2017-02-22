package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Prevents the turret from moving away from 0 while the arm is high.
 */
public class TurretMoveToZero extends Command {

    private final Collector collector = Robot.runningRobot.collector;
    private final Turret turret = Robot.runningRobot.turret;

    public TurretMoveToZero() {
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.setAngle(0);
    }

    @Override
    protected boolean isFinished() {
        return collector.getPosition() < Collector.TURRET_RESET_POSITION;
    }

}
