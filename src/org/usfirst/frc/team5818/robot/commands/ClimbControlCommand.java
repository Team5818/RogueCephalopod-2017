package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Climber;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

public class ClimbControlCommand extends ControlCommand {

    private final Climber climb = Robot.runningRobot.climb;

    public ClimbControlCommand() {
        super(js(driver -> driver.JS_CLIMB));
        requires(climb);
    }

    @Override
    protected void setPower() {
        climb.setPower(MathUtil.adjustDeadband(driver.JS_CLIMB, Driver.DEADBAND_VEC).getY());
    }

    @Override
    protected void setZero() {
        climb.setPower(0);
    }

}
