package org.usfirst.frc.team5818.robot.commands.collroll;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class InitialCollectorRoll extends Command {

    private static final double UPPER_MOTOR_POWER = 0.3;
    private static final double LOWER_MOTOR_POWER = 0.2;
    private static final double CURRENT_LIMIT = 10;
    private static final double LIMIT_EXCEED_TIME = 4; // seconds

    private final CollectorRollers collectorRollers = Robot.runningrobot.collectorRollers;

    private double timeFirstExceeded;

    @Override
    protected void execute() {
        collectorRollers.setTopPower(UPPER_MOTOR_POWER);
        collectorRollers.setBotPower(LOWER_MOTOR_POWER);
    }

    @Override
    protected void end() {
        collectorRollers.setTopPower(0);
        collectorRollers.setBotPower(0);
    }

    @Override
    protected boolean isFinished() {
        boolean overCurrently =
                Math.max(collectorRollers.getBotCurrent(), collectorRollers.getTopCurrent()) > CURRENT_LIMIT;
        if (timeFirstExceeded > 0) {
            if (overCurrently) {
                return (Timer.getFPGATimestamp() - timeFirstExceeded) > LIMIT_EXCEED_TIME;
            } else {
                timeFirstExceeded = 0;
            }
        } else if (overCurrently) {
            timeFirstExceeded = Timer.getFPGATimestamp();
        }
        return false;
    }

}
