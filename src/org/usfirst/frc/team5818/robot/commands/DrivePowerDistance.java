
package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DrivePowerDistance extends Command {

    private DriveTrain dt = Robot.runningrobot.driveTrain;
    private double power;
    private double inches;
    private double timeout;
    private double startPos;

    public DrivePowerDistance(double power, double inches, double timeout) {
        requires(Robot.runningrobot.driveTrain);
        this.power = power;
        this.inches = inches;
        this.timeout = timeout;
    }

    protected void initialize() {
        setTimeout(timeout);
        startPos = Math.abs(dt.getAvgSidePosition());
    }

    protected void execute() {
        dt.setPowerLeftRight(power, power);
    }

    protected boolean isFinished() {
        return (Math.abs(Math.abs(dt.getAvgSidePosition()) - startPos) > inches) || isTimedOut();
    }

    protected void end() {
        dt.stop();
    }

}
