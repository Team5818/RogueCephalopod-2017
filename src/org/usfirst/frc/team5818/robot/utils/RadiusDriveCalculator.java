package org.usfirst.frc.team5818.robot.utils;

public enum RadiusDriveCalculator implements DriveCalculator {
    /**
     * Computes values for "RadiusDrive" (Basically just CheesyDrive)
     */

    INSTANCE;

    private static final double kTurnSensitivity = 1.0;
    private boolean isQuickTurn = false;

    @Override
    public Vector2d compute(Vector2d input) {

        double wheel = input.getX();
        double throttle = input.getY();

        double overPower;

        double angularPower;

        if (isQuickTurn || Math.abs(throttle) < 0.2) {
            overPower = 1.0 - throttle*5;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * kTurnSensitivity;
        }

        double rightPwm = throttle + angularPower;
        double leftPwm = throttle - angularPower;
        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-leftPwm - 1.0);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-rightPwm - 1.0);
            rightPwm = -1.0;
        }
        if (Math.abs(leftPwm) > 1.0 || Math.abs(rightPwm) > 1.0) {
            leftPwm = MathUtil.limit(leftPwm, 1);
            rightPwm = MathUtil.limit(rightPwm, 1);
        }

        Vector2d output = new Vector2d(leftPwm, rightPwm);
        return output;
    }

    public void setQuick(boolean b) {
        isQuickTurn = b;
    }

}
