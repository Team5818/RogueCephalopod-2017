package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DiscoverPlacePosition extends Command {

    private enum State {
        WAITING, EXTEND, CHECK_LIMIT, TURN_TURRET, STOP_TURRET, FINISHED
    }

    private Turret turr;
    private State state;
    private State next;
    private double waitTimestamp;
    private double targetAngle;
    private int loopCount = 0;

    public DiscoverPlacePosition() {
        turr = Robot.runningRobot.turret;
    }

    private void waitThenRunState(long millis, State nextState) {
        waitTimestamp = Timer.getFPGATimestamp() + millis / 1000.0;
        state = State.WAITING;
        next = nextState;
    }

    @Override
    protected void initialize() {
        state = State.EXTEND;
        loopCount = 0;
    }

    @Override
    protected void execute() {
        switch (state) {
            case EXTEND:
                // extend and check limit later
                turr.extend(true);
                waitThenRunState(350, State.CHECK_LIMIT);
                break;
            case CHECK_LIMIT:
                // check limit
                if (!turr.getLimit() && loopCount < 2) {
                    // retract and turn turret later
                    turr.extend(false);
                    waitThenRunState(300, State.TURN_TURRET);
                } else {
                    // done
                    state = State.FINISHED;
                }
                break;
            case TURN_TURRET:
                // turn turret and stop later
                final double angleSign = Math.signum(loopCount - 1);
                targetAngle = turr.getAngle() + (angleSign * 2.5 * (loopCount + 1));
                turr.setPower(angleSign * .3);
                loopCount++;
                state = State.STOP_TURRET;
                break;
            case STOP_TURRET:
                // stop turret and extend
                if ((targetAngle - .5) < turr.getAngle() && turr.getAngle() < (targetAngle + .5)) {
                    turr.setPower(0);
                    state = State.EXTEND;
                }
                break;
            case FINISHED:
                break;
            case WAITING:
            default:
                if (Timer.getFPGATimestamp() > waitTimestamp) {
                    state = next;
                }
        }
    }

    @Override
    protected boolean isFinished() {
        return state == null || state == State.FINISHED;
    }

    public boolean isCenterPlace() {
        return loopCount == 0;
    }

}
