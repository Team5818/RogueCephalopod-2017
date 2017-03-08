package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmAngle extends Command {

    public static final double TOLERANCE = 50;

    private Arm arm;
    private double targetAng;

    public SetArmAngle(double angle) {
        setTimeout(1);
        arm = Robot.runningRobot.arm;
        targetAng = angle;
        requires(arm);
    }

    @Override
    public void initialize() {
        // reset turret if target is too high
        if (targetAng >= Arm.TURRET_RESET_POSITION) {
            Robot.runningRobot.runTurretOverrides();
        }
        arm.setBrakeMode(false);
        arm.getAnglePID().setAbsoluteTolerance(TOLERANCE);
        arm.getAnglePID().setToleranceBuffer(2);
        arm.setAngle(targetAng);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || arm.getAnglePID().onTarget();
    }

    @Override
    public void end() {
        arm.stop();
        arm.setBrakeMode(true);
    }

}