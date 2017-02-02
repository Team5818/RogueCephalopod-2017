package org.usfirst.frc.team5818.robot.utils;

public enum RadiusDriveCalculator implements DriveCalculator{
	
	INSTANCE;
	
    private static final double kTurnSensitivity = 1.0;
	private boolean isQuickTurn = false;
	private double mQuickStopAccumulator = 0.0;

	@Override
	public Vector2d compute(Vector2d input) {

        double wheel = input.getX();
        double throttle = input.getY();

        double overPower;

        double angularPower;

        if (isQuickTurn) {
            if (Math.abs(throttle) < 0.2) {
                double alpha = 0.1;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator + alpha * MathUtil.limit(wheel, 1.0) * 2;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * kTurnSensitivity - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
        }

        double rightPwm = throttle - angularPower;
        double leftPwm = throttle + angularPower;
        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }
        Vector2d output = new Vector2d(leftPwm, rightPwm);
        return output;
    }
	
	public void setQuick(boolean b){
		isQuickTurn = b;
	}

}
