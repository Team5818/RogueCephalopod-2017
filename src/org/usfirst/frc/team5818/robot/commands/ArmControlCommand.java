package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

public class ArmControlCommand extends ControlCommand {

    private final Arm arm = Robot.runningRobot.arm;

    public ArmControlCommand() {
        super(js(driver -> driver.JS_COLLECTOR));
        requires(arm);
    }

    @Override
    protected void initialize() {
        arm.setBrakeMode(true);
    }

    @Override
    protected void setPower() {
        arm.setPower(MathUtil.adjustDeadband(driver.JS_COLLECTOR, Driver.DEADBAND_VEC).getY());
    }

    @Override
    protected void setZero() {
        arm.setPower(0);
    }

}
