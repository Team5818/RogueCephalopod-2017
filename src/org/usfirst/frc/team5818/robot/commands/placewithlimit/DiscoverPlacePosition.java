/*
 * This file is part of Rogue-Cephalopod, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A smart routine that loops to try harder at placing a gear on the peg when
 * mostly aligned.
 */
public class DiscoverPlacePosition extends Command {

	/**
	 * States for DPP.
	 */
	private enum State {
		/**
		 * DPP is waiting to move to the state indicated by {@code next}.
		 */
		WAITING,
		/**
		 * DPP is extending the turret.
		 */
		EXTEND,
		/**
		 * DPP is checking the limit switch for failure.
		 */
		CHECK_LIMIT,
		/**
		 * DPP is turning the turret to the next position to place.
		 */
		TURN_TURRET,
		/**
		 * DPP is stopping the turret for the next extend attempt.
		 */
		STOP_TURRET,
		/**
		 * DPP has completed all steps.
		 */
		FINISHED
	}

	private Turret turr;
	private State state;
	private State next;
	private double waitTimestamp;
	private int loopCount = 0;

	public DiscoverPlacePosition() {
		setTimeout(5);
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
        double angle = turr.getPositionRaw();
		switch (state) {
		case EXTEND:
			// extend and check limit later
			turr.extend(true);
			waitThenRunState(450, State.CHECK_LIMIT);
			break;
		case CHECK_LIMIT:
			// check limit
			if (!turr.getLimit() && loopCount <= 2) {
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
        switch (state) {
            case EXTEND:
                // extend and check limit later
                turr.extend(true);
                waitThenRunState(450, State.CHECK_LIMIT);
                break;
            case CHECK_LIMIT:
                // check limit
                if (!turr.getLimit() && loopCount <= 2) {
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
                    target = angle + 30;
			} else /* if (loopCount == 1) */ {
                } else /* if (loopCount == 1) */ {
                    target = angle - 30;
			}
			SmartDashboard.putNumber("DPPAngle", target);
			turr.setAngle(target);
			loopCount++;
                }
                SmartDashboard.putNumber("DPPAngle", target);
                turr.setAngle(target);
                loopCount++;
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
    @Override
    protected boolean isFinished() {
        return state == null || state == State.FINISHED || isTimedOut();
    }

}
