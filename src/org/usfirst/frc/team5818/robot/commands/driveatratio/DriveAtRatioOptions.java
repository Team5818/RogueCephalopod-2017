package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;

public interface DriveAtRatioOptions {

    Camera getCamera();

    Side getRotation();

    double getInches();

    double getMaxPower();
    
    double getMinPower();
    
    double getVisionOffset();
    
    double getAccel();

    double getMaxRatio();

    double getTargetRatio();

    boolean isStoppingAtEnd();

    default boolean isUsingSanic() {
        return getCamera() == Camera.ULTRASANIC;
    }

    default boolean isUsingVision() {
        return getCamera() == Camera.CAM_TAPE || getCamera() == Camera.CAM_GEARS;
    }

    default boolean isSpinning() {
        return getRotation() == Side.LEFT || getRotation() == Side.RIGHT;
    }
    
    default boolean isProfiling(){
        return getAccel() != 0;
    }

}
