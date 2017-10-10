/*
 * This file is part of Rogue-Cephalopod, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Camera;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Interfaces with Raspberry Pi though simple serial commands to control vision
 * parameters
 */
public class CameraController extends Subsystem {

    /* exposure constants make no sense - thanks V4L2 */
    public static double CAMERA_EXPOSURE_HIGH = 156;
    public static double CAMERA_EXPOSURE_LOW = 5;

    private Camera currCam;
    private SerialPort rPi;
    private double frontExposure;

    public CameraController() {
        rPi = Robot.runningRobot.vision.getRasPi();
        currCam = Camera.CAM_GEARS;
        frontExposure = CAMERA_EXPOSURE_LOW;
    }

    public void switchFeed() {
        if (currCam.equals(Camera.CAM_GEARS)) {
            enterTapeMode();
        } else {
            enterGearMode();
        }
    }

    public void enterGearMode() {
        try {
            rPi.writeString("g");
            /*
             * RPi will react by switching camera feed to the collector camera
             */
            currCam = Camera.CAM_GEARS;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterTapeMode() {
        try {
            rPi.writeString("t");
            /* RPi will react by switching camera feed to the placer camera */
            currCam = Camera.CAM_TAPE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchExposure() {
        if (frontExposure == CAMERA_EXPOSURE_LOW) {
            setHighExposure();
        } else {
            setLowExposure();
        }
    }

    public void setHighExposure() {
        try {
            rPi.writeString("h");
            /* RPi will react by increasing exposure */
            frontExposure = CAMERA_EXPOSURE_HIGH;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLowExposure() {
        try {
            rPi.writeString("l");
            /* RPi will react by decreasing exposure */
            frontExposure = CAMERA_EXPOSURE_LOW;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        try {
            rPi.writeString("s");
            /* RPi will react by shutting itself down safely */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Camera getCurrentCam() {
        return currCam;
    }

    public boolean isTape() {
        return currCam.equals(Camera.CAM_TAPE);
    }

    public double getFrontExposure() {
        return frontExposure;
    }

    @Override
    protected void initDefaultCommand() {
    }

}
