package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DiscoverPlacePosition extends Command {

    private enum State {
        WAITING, EXTEND, CHECK_LIMIT, TURN_TURRET, STOP_TURRET, FINISHED
    }

    private Turret turr;
    private State state;
    private State next;
    private double waitTimestamp;
    private int loopCount = 0;

    public DiscoverPlacePosition() {
        setTimeout(2);
        turr = Robot.runningRobot.turret;
        requires(turr.rotator);
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
        double angle = turr.getPosition();
        switch (state) {
            case EXTEND:
                // extend and check limit later
                turr.extend(true);
                waitThenRunState(450, State.CHECK_LIMIT);
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
                double target;
                if (loopCount == 0) {
                    target = Turret.TURRET_CENTER_POS + 100;
                } else /* if (loopCount == 1) */ {
                    target = Turret.TURRET_CENTER_POS - 100;
                }
                SmartDashboard.putNumber("DPPAngle", target);
                turr.setAngle(target);
                loopCount++;
                state = State.STOP_TURRET;
                waitThenRunState(300, State.STOP_TURRET);
                break;
            case STOP_TURRET:
                SmartDashboard.putNumber("DPPAngleEnd", angle);
                turr.setPower(0);
                state = State.EXTEND;
                break;
            case FINISHED:
                break;
            case WAITING:
            default:
                if (Timer.getFPGATimestamp() > waitTimestamp) {
                    state = next;
                    next = null;
                }
        }
    }

    @Override
    protected boolean isFinished() {
        return state == null || state == State.FINISHED || isTimedOut();
    }

}
