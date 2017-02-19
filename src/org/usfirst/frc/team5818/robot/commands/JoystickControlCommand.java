package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.Vector2d;
import org.usfirst.frc.team5818.robot.utils.Vectors;

import edu.wpi.first.wpilibj.command.Command;

public class JoystickControlCommand extends Command {

    private final Driver DRIVER = Robot.runningrobot.driver;
    private final DriveTrain driveTrain = Robot.runningrobot.driveTrain;

    public JoystickControlCommand() {
        requires(driveTrain);
    }

    @Override
    protected void execute() {
        Vector2d driveVector =
                Vectors.fromJoystick(DRIVER.JS_FW_BACK, DRIVER.JS_TURN, true);
        Vector2d controlVector = DRIVER.driveCalc.compute(driveVector);
        switch (DRIVER.dMode) {
            case POWER:
                driveTrain.setPowerLeftRight(controlVector);
                break;
            case VELOCITY:
                driveTrain.setVelocityLeftRight(
                        controlVector.scale(BotConstants.ROBOT_MAX_VELOCITY));
                break;
            default:
                driveTrain.stop();
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
