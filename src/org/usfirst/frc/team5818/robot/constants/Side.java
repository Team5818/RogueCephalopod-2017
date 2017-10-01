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
