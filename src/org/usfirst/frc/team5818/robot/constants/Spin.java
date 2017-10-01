package org.usfirst.frc.team5818.robot.constants;

/**
 * A spin direction.
 */
public enum Spin {
    CLOCKWISE, COUNTERCW;

    public Spin theOtherWay() {
        if (this == CLOCKWISE) {
            return COUNTERCW;
        } else {
            return CLOCKWISE;
        }
    }
}
