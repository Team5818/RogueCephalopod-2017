package org.usfirst.frc.team5818.robot.utils;

import org.usfirst.frc.team5818.robot.Robot;

public enum RatioDriveCalculator implements DriveCalculator {

    INSTANCE;

    private static final double MAX_RATIO = 5.0;
    private boolean isQuickTurn = false;
    private static final double TURN_SENSITIVITY = .8;

    @Override
    public Vector2d compute(Vector2d input) {

        double drivePower = input.getY();
        double anglePower = input.getX() * Math.signum(drivePower);
        double targetRatio = Math.pow(MAX_RATIO, anglePower);

        double leftVel = Math.abs(Robot.runningRobot.driveTrain.left.getSideVelocity());
        double rightVel = Math.abs(Robot.runningRobot.driveTrain.right.getSideVelocity());
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }

        double leftPowMult = 1.0;
        double rightPowMult = currRatio / Math.pow(targetRatio, 2);

        Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
        driveVec = driveVec.normalize(drivePower);

        if (isQuickTurn) {
            double leftPwm = driveVec.getX() + input.getX() * TURN_SENSITIVITY;
            double rightPwm = driveVec.getY() - input.getX() * TURN_SENSITIVITY;

            if (leftPwm > 1.0) {
                rightPwm -= (leftPwm - 1.0);
                leftPwm = 1.0;
            } else if (rightPwm > 1.0) {
                leftPwm -= (rightPwm - 1.0);
                rightPwm = 1.0;
            } else if (leftPwm < -1.0) {
                rightPwm += (-leftPwm - 1.0);
                leftPwm = -1.0;
            } else if (rightPwm < -1.0) {
                leftPwm += (-rightPwm - 1.0);
                rightPwm = -1.0;
            }

            driveVec = new Vector2d(leftPwm, rightPwm);
        }

        return driveVec;
    }

    public void setQuick(boolean b) {
        isQuickTurn = b;
    }

}
