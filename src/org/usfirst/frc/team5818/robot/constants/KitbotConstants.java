package org.usfirst.frc.team5818.robot.constants;

public class KitbotConstants extends Constants {

    KitbotConstants() {
    }

    @Override
    public double wheelToWheelWidth() {
        return 24;
    }

    @Override
    public double turretScale() {
        return 0.05535;
    }

    @Override
    public double turretCenter() {
        return 1689.0;
    }

    @Override
    public double encoderScale() {
        return 4.0 / 44.815;
    }

    @Override
    public double maxVelocityIPS() {
        return 120.0;
    }

    @Override
    public double maxAccelIPS2() {
        return 200;
    }

}
