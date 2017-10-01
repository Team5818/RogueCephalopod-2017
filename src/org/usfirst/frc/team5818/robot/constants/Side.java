package org.usfirst.frc.team5818.robot.constants;

/**
 * A side of the robot.
 */
public enum Side {
    LEFT, RIGHT, CENTER;

    public Side other() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return CENTER;
        }
    }

    public double adjustRatio(double right, Direction dir) {
        if (this == CENTER) {
            throw new IllegalArgumentException("CENTER cannot be used for ratios");
        }
        if (dir == Direction.FORWARD) {
            if (this == RIGHT) {
                return right;
            } else {
                return 1.0 / right;
            }
        } else {
            if (this == RIGHT) {
                return 1.0 / right;
            } else {
                return right;
            }
        }
    }
}
