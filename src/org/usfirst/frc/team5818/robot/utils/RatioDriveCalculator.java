package org.usfirst.frc.team5818.robot.utils;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public enum RatioDriveCalculator implements DriveCalculator {

    INSTANCE;

    private static final double MAX_RATIO = 5.0;
    private boolean isQuickTurn = false;

    @Override
    public Vector2d compute(Vector2d input) {

        double anglePower = input.getX();
        double drivePower = input.getY();
        double targetRatio = Math.pow(MAX_RATIO, anglePower);

        double leftVel = Math.abs(Robot.runningRobot.driveTrain.left.getSideVelocity());
        double rightVel = Math.abs(Robot.runningRobot.driveTrain.right.getSideVelocity());
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }
        
        double leftPowMult = 1.0;
        double rightPowMult = currRatio / Math.pow(targetRatio,2);

        Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
        driveVec = driveVec.normalize(drivePower);

        if(isQuickTurn){
            double leftPwm = driveVec.getX() + anglePower;
            double rightPwm = driveVec.getX() - anglePower;

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
