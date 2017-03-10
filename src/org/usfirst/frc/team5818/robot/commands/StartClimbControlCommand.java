package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StartClimbControlCommand extends Command {

    private enum State {
        CANCEL_ZERO, START_CCC, FINISHED;
    }

    private State state = State.CANCEL_ZERO;

    @Override
    protected void execute() {
        switch (state) {
            case CANCEL_ZERO:
                Robot.runningRobot.turretSafetyChecks = false;
                Robot.runningRobot.turretZero.cancel();
                state = State.START_CCC;
                break;
            case START_CCC:
            default:
                new ClimbMode().start();
                state = State.FINISHED;
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return state == State.FINISHED;
    }

}
