package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DeadReckonOpts.Builder;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;

import com.google.auto.value.AutoValue;

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
    public final Camera getCamera() {
        return Camera.NONE;
    }

    @Override
    public final Side getRotation() {
        return Side.CENTER;
    }

    @Override
    public abstract double getInches();

    @Override
    public abstract double getMaxPower();

    @Override
    public final double getMaxRatio() {
        return 1.0;
    }
    
    @Override
    public double getVisionOffset(){
        return 0.0;
    }

    @Override
    public abstract double getTargetRatio();

    @Override
    public abstract boolean isStoppingAtEnd();
}
