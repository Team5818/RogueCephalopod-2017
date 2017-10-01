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
package org.usfirst.frc.team5818.robot.utils;

/**
 * A {@link DriveCalculator} that computes values for arcade drive.
 */
public enum ArcadeDriveCalculator implements DriveCalculator {
    /**
     * The only instance of this calculator.
     */
    INSTANCE;

    public static final int JOYSTICK_MODE_CURVE = 1;
    public static final int JOYSTICK_MODE_REGULAR = 0;

    private static int joystickMode = JOYSTICK_MODE_CURVE;
    private static double turnMult = 1;
    private static double turnPower = 1.5;

    private static double forwardMult = 1;
    private static double forwardPower = 1;
    private static double deadband = .2;

    @Override
    public Vector2d compute(Vector2d leftAndRight) {
        if (joystickMode == JOYSTICK_MODE_CURVE)
            return computeTurnsDifferent(leftAndRight);
        else
            return computeDefault(leftAndRight);
    }

    public Vector2d computeDefault(Vector2d in) {
        double rotateValue = handleDeadband(-in.getX());
        double moveValue = handleDeadband(in.getY());
        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }

    public Vector2d computeTurnsDifferent(Vector2d in) {
        double rotateValue =
                Math.signum(-in.getX()) * Math.pow(Math.abs(handleDeadband(in.getX())), turnPower) * turnMult; // Less
        double moveValue =
                Math.signum(in.getY()) * Math.pow(Math.abs(handleDeadband(-in.getY())), forwardPower) * forwardMult; // Less

        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }

    public int getJoystickMode(int mode) {
        return joystickMode;
    }

    public double handleDeadband(double in) {
        if (Math.abs(in) > deadband) {
            return in;
        }
        return 0.0;
    }

}
