package org.usfirst.frc.team5818.robot.commands.driveatratio;

import java.util.EnumSet;
import java.util.Set;

import org.usfirst.frc.team5818.robot.constants.Camera;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VisionOpts implements DriveAtRatioOptions {

    private static final Set<Camera> VIS_CAMS =
            EnumSet.of(Camera.CAM_FORWARD, Camera.CAM_BACKWARD);

    public static Builder builder(Camera camera) {
        if (!VIS_CAMS.contains(camera)) {
            throw new IllegalArgumentException(
                    "Camera must be one of " + VIS_CAMS);
        }
        return new AutoValue_VisionOpts.Builder().camera(camera);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        abstract Builder camera(Camera value);

        public abstract Builder inches(double value);

        public abstract Builder maxPower(double value);

        public abstract Builder maxRatio(double value);

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
    public final double getTargetRatio() {
        return 1.0;
    }

    @Override
    public abstract boolean isStoppingAtEnd();

}
