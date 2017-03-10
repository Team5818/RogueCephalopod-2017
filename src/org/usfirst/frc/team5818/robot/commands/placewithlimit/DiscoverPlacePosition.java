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
    private double startAngle;
    private double targetAngle;
    private int loopCount = 0;

    public DiscoverPlacePosition() {
        turr = Robot.runningRobot.turret;
        requires(turr);
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
        double angle = turr.getAngle();
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
                final double angleSign;
                if (loopCount == 0) {
                    angleSign = 1;
                } else /* if (loopCount == 1) */ {
                    angleSign = -1;
                }
                startAngle = angle;
                targetAngle = startAngle + (angleSign * 2.5 * (loopCount + 1));
                SmartDashboard.putNumber("DPPAngle", targetAngle);
                turr.setPower(angleSign * .3);
                loopCount++;
                state = State.STOP_TURRET;
                break;
            case STOP_TURRET:
                // stop turret and extend
                boolean passedTarget1 = startAngle < targetAngle && targetAngle < angle;
                boolean passedTarget2 = angle < targetAngle && targetAngle < startAngle;
                if (passedTarget1 || passedTarget2) {
                    SmartDashboard.putNumber("DPPAngleEnd", angle);
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

}
