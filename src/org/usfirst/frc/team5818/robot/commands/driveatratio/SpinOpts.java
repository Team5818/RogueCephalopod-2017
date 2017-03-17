package org.usfirst.frc.team5818.robot.commands.driveatratio;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SpinOpts implements DriveAtRatioOptions {

    public static Builder builder() {
        return new AutoValue_SpinOpts.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

        default Builder angle(double deg) {
            double rad = Math.toRadians(deg);
            return inches(rad * Constant.wheelToWheelWidth() / 2.0);
        }

        Builder inches(double value);

        Builder maxPower(double value);

        Builder rotation(Spin value);

        Builder stoppingAtEnd(boolean value);

        SpinOpts build();

    }

    SpinOpts() {
    }

    @Override
    public abstract Spin getRotation();

    @Override
    public abstract double getInches();

    @Override
    public abstract double getMaxPower();

    @Override
    public abstract boolean isStoppingAtEnd();

    @Override
    public final Camera getCamera() {
        return Camera.NONE;
    }

    @Override
    public final double getMaxRatio() {
        return 1.0;
    }

    @Override
    public final double getAccel() {
        return 0.0;
    }

    @Override
    public final double getMinPower() {
        return 0.0;
    }

    @Override
    public final double getVisionOffset() {
        return 0.0;
    }

    @Override
    public final double getTargetRatio() {
        return 1.0;
    }

}
