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

import org.usfirst.frc.team5818.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for robot's arm. Has 2 775 pros and a Vex absolute encoder. Uses
 * Motion Magic to move.
 */
public class Arm extends Subsystem {

    /* Important positions and angles */
    public static final double COLLECT_POSITION = 701;
    public static final double CLIMB_POSITION = 2200;
    public static final double MID_POSITION = 2366;
    public static final double NINETY_DEGREES = 2400;
    public static final double SLOT_COLLECT_POSITION = NINETY_DEGREES;
    public static final double TURRET_RESET_POSITION = NINETY_DEGREES;
    public static final double LOAD_POSITION = 3275;

    /* soft limits on arm position */
    private double limitLow = COLLECT_POSITION;
    private double limitHigh = LOAD_POSITION;

    /* Talons */
    private CANTalon slaveTal;
    private CANTalon masterTal;

    public Arm() {
        masterTal = new CANTalon(RobotMap.ARM_TALON_R);
        masterTal.setInverted(true);
        masterTal.reverseOutput(true);
        masterTal.setForwardSoftLimit(limitHigh);
        masterTal.setReverseSoftLimit(limitLow);
        slaveTal = new CANTalon(RobotMap.ARM_TALON_L);
        slaveTal.changeControlMode(TalonControlMode.Follower);
        slaveTal.set(RobotMap.ARM_TALON_R);
        slaveTal.reverseOutput(true);

        /* Set up motion profiling constants */
        masterTal.configEncoderCodesPerRev(4096 * 2);// 2:1 sprocket reduction
        masterTal.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        masterTal.setF(1023.0 / 360.0);
        masterTal.setP(2.4 * 1023.0 / 1000);
        masterTal.setI(0);
        masterTal.setD(50.0 * 1023.0 / 1000);
        masterTal.setMotionMagicAcceleration(40);
        masterTal.setMotionMagicCruiseVelocity(35);// 80% max
        masterTal.changeControlMode(TalonControlMode.MotionMagic);

        setBrakeMode(true);
    }

    public void setBrakeMode(boolean mode) {
        slaveTal.enableBrakeMode(mode);
        masterTal.enableBrakeMode(mode);
    }

    public void setPower(double x) {
        masterTal.changeControlMode(TalonControlMode.PercentVbus);
        setBrakeMode(true);
        if (getPositionRaw() <= limitLow) {
            x = Math.max(x, 0.0);
            DriverStation.reportError("Below ", false);
         } 
        else if (getPositionRaw() >= limitHigh) {
            x = Math.min(x, 0);
        }
        masterTal.set(x);
        SmartDashboard.putNumber("Arm Power", x);
    }

    public void setManual() {
        masterTal.changeControlMode(TalonControlMode.PercentVbus);
    }

    public void setAngle(double angle) {
        angle = angle / 4096.0; // inputs in native units, just to make things
                                // harder
        DriverStation.reportError("" + angle, false);
        masterTal.setEncPosition(masterTal.getPulseWidthPosition());
        setBrakeMode(false);
        masterTal.changeControlMode(TalonControlMode.MotionMagic);
        masterTal.set(angle);
    }

    public double getPositionRaw() {
        return masterTal.getPulseWidthPosition();
    }

    public double getPosition() {
        masterTal.setEncPosition(masterTal.getPulseWidthPosition());
        return masterTal.getPosition();
    }

    public double getVelocity() {
        return masterTal.getSpeed();
    }

    public void stop() {
        setBrakeMode(true);
        setManual();
        masterTal.set(0);
    }

    public void setLimitLow(double limitLow) {
        this.limitLow = limitLow;
    }

    public void setLimitHigh(double limitHigh) {
        this.limitHigh = limitHigh;
    }

    public int getError() {
        return masterTal.getClosedLoopError();
    }

    @Override
    protected void initDefaultCommand() {
    }

}