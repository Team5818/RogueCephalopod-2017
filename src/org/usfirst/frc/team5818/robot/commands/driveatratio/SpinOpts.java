package org.usfirst.frc.team5818.robot.commands.driveatratio;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;

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

        Builder rotation(Side value);

        Builder stoppingAtEnd(boolean value);

        SpinOpts build();

    }

    SpinOpts() {
    }

    @Override
    public final Camera getCamera() {
        return Camera.NONE;
    }

    @Override
    public abstract Side getRotation();

    @Override
    public abstract double getInches();

    @Override
    public abstract double getMaxPower();

    @Override
    public final double getMaxRatio() {
        return 1.0;
    }

    @Override
    public final double getTargetRatio() {
        return 1.0;
    }

    @Override
    public abstract boolean isStoppingAtEnd();

}