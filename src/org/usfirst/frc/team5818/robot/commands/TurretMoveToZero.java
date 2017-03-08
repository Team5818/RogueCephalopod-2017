package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Prevents the turret from moving away from 0 while the arm is high.
 */
public class TurretMoveToZero extends Command {

    private final Arm arm = Robot.runningRobot.arm;
    private final Turret turret = Robot.runningRobot.turret;

    public TurretMoveToZero() {
        requires(turret);
        setInterruptible(false);
    }

    @Override
    protected void initialize() {
        turret.setAngle(0);
    }

    @Override
    protected boolean isFinished() {
        return arm.getPosition() < Arm.TURRET_RESET_POSITION;
    }

}
