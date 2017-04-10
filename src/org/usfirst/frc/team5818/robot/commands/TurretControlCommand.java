package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

public class TurretControlCommand extends ControlCommand {

    private final Turret turret = Robot.runningRobot.turret;

    public TurretControlCommand() {
        super(js(driver -> driver.JS_TURRET));
        requires(turret.rotator);
    }

    @Override
    protected void setPower() {
        turret.setPower(MathUtil.adjustDeadband(driver.JS_TURRET, Driver.DEADBAND_VEC).getX());
    }

    @Override
    protected void setZero() {
        turret.setPower(0);
    }

}
