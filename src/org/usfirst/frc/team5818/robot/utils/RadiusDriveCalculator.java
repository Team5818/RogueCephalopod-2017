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
 * Computes values for "RadiusDrive" (Basically just CheesyDrive with a little
 * magic sauce)
 */
public enum RadiusDriveCalculator implements DriveCalculator {

    INSTANCE;

    private static final double kTurnSensitivity = 1.0;
    private boolean isQuickTurn = false;

    @Override
    public Vector2d compute(Vector2d input) {

        double wheel = input.getX();
        double throttle = input.getY();

        double overPower;

        double angularPower;

        if (isQuickTurn) {
            overPower = 1.0 - throttle * 5;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            if (Math.abs(throttle) < .2) {
                overPower = 1.0 - Math.abs(throttle) * 5.0;
            }
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
