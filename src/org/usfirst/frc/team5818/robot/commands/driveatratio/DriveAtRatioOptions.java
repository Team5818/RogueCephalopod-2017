package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.constants.Camera;

public interface DriveAtRatioOptions {

    Camera getCamera();

    double getInches();

    double getMaxPower();

    double getMaxRatio();

    double getTargetRatio();

    boolean isStoppingAtEnd();

    default boolean isUsingSanic() {
        return getCamera() == Camera.ULTRASANIC;
    }

    default boolean isUsingVision() {
        return getCamera() == Camera.CAM_FORWARD || getCamera() == Camera.CAM_BACKWARD;
    }

}
