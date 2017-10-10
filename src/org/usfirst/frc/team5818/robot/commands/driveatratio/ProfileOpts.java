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
package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

import com.google.auto.value.AutoValue;

/**
 * DAR options for using profiles to guide the routine.
 */
@AutoValue
public abstract class ProfileOpts implements DriveAtRatioOptions {

    public static Builder builder() {
        return new AutoValue_ProfileOpts.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

        Builder inches(double value);

        Builder minPower(double value);

        Builder accel(double value);

        Builder maxPower(double value);

        Builder targetRatio(double value);

        Builder stoppingAtEnd(boolean value);

        ProfileOpts build();

    }

    ProfileOpts() {
    }

    @Override
    public abstract double getInches();

    @Override
    public abstract double getMaxPower();

    @Override
    public abstract double getTargetRatio();

    @Override
    public abstract boolean isStoppingAtEnd();

    @Override
    public final Camera getCamera() {
        return Camera.NONE;
    }

    @Override
    public final Spin getRotation() {
        return null;
    }

    @Override
    public final double getMaxRatio() {
        return 1.0;
    }

    @Override
    public double getVisionOffset() {
        return 0.0;
    }

}
