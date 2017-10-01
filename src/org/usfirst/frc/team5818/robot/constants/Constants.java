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
package org.usfirst.frc.team5818.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Constants class. Implemented by different instances for different robots.
 */
public abstract class Constants {

    private static final String COMP_BOT = "rogue-cephalopod";
    /**
     * The constant instance for this robot.
     */
    public static final Constants Constant;
    static {
        String robotId = Preferences.getInstance().getString("robot-id", COMP_BOT);
        switch (robotId) {
            case "kitbot":
                Constant = new KitbotConstants();
                break;
            case "rogue-cephalopod":
                Constant = new RogueCephalopod();
                break;
            default:
                throw new IllegalStateException("Illegal robot ID!!");
        }
    }

    public abstract double turretScale();

    public abstract double turretCenter();

    public abstract double encoderScale();

    public abstract double maxVelocityIPS();

    public abstract double maxAccelIPS2();

    public abstract double wheelToWheelWidth();

    public final double maxPower() {
        return 1.0;
    }

    public final int joystickForwardBack() {
        return 0;
    }

    public final int joystickTurn() {
        return 1;
    }

    public final int joystickTurret() {
        return 2;
    }

    public final int joystickCollector() {
        return 3;
    }

    public final double maxVelocity() {
        return 500;
    }

    public final double cameraFov() {
        return 60;
    }

    public final double cameraWidth() {
        return 160;
    }

}
