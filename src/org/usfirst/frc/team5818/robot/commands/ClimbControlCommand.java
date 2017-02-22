package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Climber;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;

public class ClimbControlCommand extends ControlCommand {

    private final Climber climb = Robot.runningRobot.climb;
    private final Turret turret = Robot.runningRobot.turret;
    private final Joystick js;

    public ClimbControlCommand(Joystick joystick) {
        super(joystick);
        js = joystick;
        requires(climb);
        requires(turret);
        setInterruptible(false);
    }

    @Override
    protected void setPower() {
        climb.setPower(MathUtil.adjustDeadband(js, Driver.DEADBAND_VEC).getY());
        turret.setPower(0.0);
    }

    @Override
    protected void setZero() {
        climb.setPower(0.0);
        turret.setPower(0.0);
    }

}
