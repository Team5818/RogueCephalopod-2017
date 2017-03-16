package org.usfirst.frc.team5818.robot.commands.driveatratio;

import java.util.EnumSet;
import java.util.Set;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;

import com.google.auto.value.AutoValue;

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
    public final Side getRotation() {
        return Side.CENTER;
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
