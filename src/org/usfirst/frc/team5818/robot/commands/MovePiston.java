package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class MovePiston extends Command {

    private Turret turr;

    public enum Position {
        RETRACT, EXTEND, PLACE_LEFT, PLACE_RIGHT, PLACE_STRAIGHT
    }

    private Position target;

    private boolean done;

    public MovePiston(Position pos) {
        target = pos;
        turr = Robot.runningRobot.turret;

        done = false;
    }

    public void initialize() {
        switch (target) {
            case RETRACT:
                turr.extend(false);
                turr.punch(false);
                turr.leftMini(false);
                turr.rightMini(false);
                break;
            case EXTEND:
                turr.extend(true);
                turr.punch(false);
                turr.leftMini(false);
                turr.rightMini(false);
                break;
            case PLACE_LEFT:
                turr.extend(true);
                turr.punch(true);
                turr.leftMini(false);
                turr.rightMini(true);
                break;
            case PLACE_RIGHT:
                turr.extend(true);
                turr.punch(true);
                turr.leftMini(true);
                turr.rightMini(false);
                break;
            case PLACE_STRAIGHT:
                turr.extend(true);
                turr.punch(true);
                turr.leftMini(true);
                turr.rightMini(true);
                break;
        }

        done = true;
    }

    @Override
    protected boolean isFinished() {
        return done;
    }
}