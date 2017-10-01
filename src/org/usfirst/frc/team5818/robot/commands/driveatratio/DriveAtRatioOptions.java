package org.usfirst.frc.team5818.robot.commands.driveatratio;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

/**
 * The options used by {@link DriveAtRatio}. Uses one of the implementations to
 * properly configure each option.
 */
public interface DriveAtRatioOptions {

	Camera getCamera();

	Spin getRotation();

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
		return getRotation() != null;
	}

	default boolean isProfiling() {
		return getAccel() != 0;
	}

}
