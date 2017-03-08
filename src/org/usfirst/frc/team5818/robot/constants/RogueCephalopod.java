package org.usfirst.frc.team5818.robot.constants;

public final class RogueCephalopod extends Constants {

    RogueCephalopod() {
    }

    @Override
    public double turretScale() {
        return -90.0 / 100.0;
    }

    @Override
    public double turretCenter() {
        return 1920;
    }
    
    @Override
    public double encoderScale(){
        return 4.0 * 120.0 / 3624.0;
    }

}
