package org.usfirst.frc.team5818.robot.constants;

/**
 * Implementation of constants for the real robot.
 */
public final class RogueCephalopod extends Constants {

    RogueCephalopod() {
    }

    @Override
    public double wheelToWheelWidth() {
        return 27.25;
    }

    @Override
    public double turretScale() {
        return -90.0 / 100.0;
    }

    @Override
    public double turretCenter() {
        return 1921;
    }

    @Override
    public double encoderScale() {
        return 4.0 * (72.0 / 2180.0);
    }

    @Override
    public double maxVelocityIPS() {
        return 72.0;
    }

    @Override
    public double maxAccelIPS2() {
        return 150;
    }
}
