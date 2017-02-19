package org.usfirst.frc.team5818.robot.utils;

/**
 * A {@link DriveCalculator} that computes values for tank drive.
 */
public enum TankDriveCalculator implements DriveCalculator {
    /**
     * The only instance of the calculator.
     */
    INSTANCE;

    @Override
    public Vector2d compute(Vector2d in) {
        double leftMotorSpeed = in.getX();
        double rightMotorSpeed = in.getY();
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }

}
