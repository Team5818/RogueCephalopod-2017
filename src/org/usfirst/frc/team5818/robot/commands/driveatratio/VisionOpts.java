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

import java.util.EnumSet;
import java.util.Set;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

import com.google.auto.value.AutoValue;

/**
 * DAR options for using vision to guide the routine.
 */
@AutoValue
public abstract class VisionOpts implements DriveAtRatioOptions {

    private static final Set<Camera> VIS_CAMS = EnumSet.of(Camera.CAM_TAPE, Camera.CAM_GEARS);

    public static Builder builder(Camera camera) {
        if (!VIS_CAMS.contains(camera)) {
            throw new IllegalArgumentException("Camera must be one of " + VIS_CAMS);
        }
        return new AutoValue_VisionOpts.Builder().camera(camera).visionOffset(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        abstract Builder camera(Camera value);

        public abstract Builder inches(double value);

        public abstract Builder maxPower(double value);

        public abstract Builder maxRatio(double value);

        public abstract Builder visionOffset(double offset);

        public abstract Builder stoppingAtEnd(boolean value);

        public abstract VisionOpts build();

    }

    @Override
    public abstract Camera getCamera();

    @Override
    public abstract double getInches();

    @Override
    public abstract double getMaxPower();

    @Override
    public abstract double getMaxRatio();

    @Override
    public abstract double getVisionOffset();

    @Override
    public abstract boolean isStoppingAtEnd();

    @Override
    public final double getAccel() {
        return 0;
    }

    @Override
    public final Spin getRotation() {
        return null;
    }

    @Override
    public final double getTargetRatio() {
        return 1.0;
    }

    @Override
    public final double getMinPower() {
        return 0.0;
    }

}
