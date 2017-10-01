package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Control command for the arm.
 */
public class ArmControlCommand extends ControlCommand {

    private final Arm arm = Robot.runningRobot.arm;
    private Joystick joystick;

    public ArmControlCommand(Joystick joy) {
        super(joy);
        joystick = joy;
        requires(arm);
    }

    @Override
    protected void initialize() {
        arm.setBrakeMode(true);
        arm.setManual();
    }

    @Override
    protected void setPower() {
        arm.setPower(MathUtil.adjustDeadband(joystick, Driver.DEADBAND_VEC).getY());
    }

    @Override
    protected void setZero() {
        arm.setPower(0);
    }

}
