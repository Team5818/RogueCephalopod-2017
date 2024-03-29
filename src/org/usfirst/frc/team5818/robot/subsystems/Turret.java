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
import org.usfirst.frc.team5818.robot.commands.TurretControlCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem representing turreted gear placer. Has 2-stage pneumatic extender
 * and turreted base. Rotates with motion magic.
 */
public class Turret {

    public static final double TURRET_CENTER_POS = 572.0;
    public static final double TURRET_LEFT_POS = 785;
    public static final double TURRET_RIGHT_POS = 315;
    public static final double TURRET_LEFT_POS_SP = 0.0;
    public static final double TURRET_RIGHT_POS_SP = 0.0;

    /**
     * Rotating portion of turret
     */
    private static final class Rotator extends Subsystem {

        @Override
        protected void initDefaultCommand() {
            setDefaultCommand(new TurretControlCommand());
        }
    }

    /**
     * Gear placing portion of turret
     */
    private static final class Deployer extends Subsystem {

        @Override
        protected void initDefaultCommand() {
        }
    }

    public final Subsystem rotator = new Rotator();
    public final Subsystem deployer = new Deployer();

    private CANTalon motor;

    private DigitalInput limitSwitch;
    private Solenoid puncher;
    private Solenoid extender;

    public Turret() {
        motor = new CANTalon(RobotMap.TURR_MOTOR);
        motor.setInverted(true);
        motor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);

        /* Set up motion profiling constants */
        motor.configPotentiometerTurns(1);
        motor.setF(1023.0 / 100.0);
        motor.setP(1023.0 / 100.0);
        motor.setI(0.0);
        motor.setD(0.0);
        motor.setMotionMagicAcceleration(250.0);
        motor.setMotionMagicCruiseVelocity(80.0);// 80% max
        motor.changeControlMode(TalonControlMode.MotionMagic);

        limitSwitch = new DigitalInput(RobotMap.TURRET_LIMIT_SWITCH);
        puncher = new Solenoid(RobotMap.TURRET_PUNCHER_SOLENOID);
        extender = new Solenoid(RobotMap.TURRET_EXTENDER_SOLENOID);
    }

    public double getVeleocity() {
        return motor.getAnalogInVelocity();
    }

    public boolean getLimit() {
        return limitSwitch.get();
    }

    public void setAngle(double ang) {
        ang = ang / 1024.0; // inputs in native units, just to make things
                            // harder
        motor.changeControlMode(TalonControlMode.MotionMagic);
        motor.set(ang);
    }

    public double getPositionRaw() {
        return motor.getAnalogInPosition();
    }

    public double getPosition() {
        return motor.getPosition();
    }

    public void setPower(double x) {
        motor.changeControlMode(TalonControlMode.PercentVbus);
        motor.set(x);
    }

    public void setManual() {
        motor.changeControlMode(TalonControlMode.PercentVbus);
    }

    public void extend(boolean on) {
        extender.set(on);
    }

    public void punch(boolean on) {
        puncher.set(on);
    }

}
