package org.usfirst.frc.team5818.robot.commands;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

import edu.wpi.first.wpilibj.Joystick;

public class OverrideControlCommand extends ControlCommand {

    private final DriveTrain driveTrain = Robot.runningRobot.driveTrain;
    private final Joystick js;

    public OverrideControlCommand(Joystick joystick) {
        super(joystick);
        js = joystick;
        requires(driveTrain);
    }

    @Override
    protected void setPower() {
        Vector2d driveVector = Vectors.fromJoystick(js, true);
        driveVector = MathUtil.adjustDeadband(driveVector, Driver.DEADBAND_VEC);
        RadiusDriveCalculator.INSTANCE.setQuick(Math.abs(js.getTwist()) > Driver.TWIST_DEADBAND);
        Vector2d controlVector = driver.driveCalc.compute(driveVector);
        switch (driver.dMode) {
            case POWER:
                driveTrain.setPowerLeftRight(controlVector);
                break;
            case VELOCITY:
                driveTrain.setVelocityLeftRight(controlVector.scale(Constant.maxVelocity()));
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
