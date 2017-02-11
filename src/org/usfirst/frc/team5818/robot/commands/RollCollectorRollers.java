package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class RollCollectorRollers extends Command {

    public enum StopCondition {
        CURRENT_SPIKE, LASER_SENSOR
    }

    private final StopCondition stopCondition;
    private final CollectorRollers collectorRollers = Robot.runningrobot.collectorRollers;

    public RollCollectorRollers(StopCondition stopCondition) {
        this.stopCondition = stopCondition;
        requires(this.collectorRollers);
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        switch (this.stopCondition) {
            case CURRENT_SPIKE:
                
                return false;
            case LASER_SENSOR:
                return false;
            default:
                throw new AssertionError("unhandled state " + this.stopCondition);
        }
    }

}
