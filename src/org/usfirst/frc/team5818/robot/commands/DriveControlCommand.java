package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

public class DriveControlCommand extends ControlCommand {

    private final DriveTrain driveTrain = Robot.runningRobot.driveTrain;

    public DriveControlCommand() {
        super(js(driver -> driver.JS_FW_BACK), js(driver -> driver.JS_TURN));
        requires(driveTrain);
    }

    @Override
    protected void setPower() {
        Vector2d driveVector = Vectors.fromJoystick(driver.JS_FW_BACK, driver.JS_TURN, true);
        driveVector = MathUtil.adjustDeadband(driveVector, Driver.DEADBAND_VEC);
        RadiusDriveCalculator.INSTANCE
                .setQuick(Math.abs(driver.JS_TURN.getTwist()) > Driver.TWIST_DEADBAND || driveVector.getY() == 0);
        Vector2d controlVector = driver.driveCalc.compute(driveVector);
        switch (driver.dMode) {
            case POWER:
                driveTrain.setPowerLeftRight(controlVector);
                break;
            case VELOCITY:
                driveTrain.setVelocityLeftRight(controlVector.scale(BotConstants.ROBOT_MAX_VELOCITY));
                break;
            default:
                driveTrain.stop();
                break;
        }
    }

    @Override
    protected void setZero() {
        driveTrain.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
