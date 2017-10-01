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

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem. Contains a talon for each side of the climbing mechanism,
 * both controlled by one power variable.
 */
public class Climber extends Subsystem {

    private CANTalon left1;
    private CANTalon right1;

    public Climber() {
        left1 = new CANTalon(RobotMap.LEFT_CLIMB_TALON_1);
        right1 = new CANTalon(RobotMap.RIGHT_CLIMB_TALON_1);
        right1.setInverted(true);
    }

    public void setPower(double pow) {
        left1.set(pow);
        right1.set(pow);
    }

    public double getLeftCurrent() {
        return left1.getOutputCurrent();
    }

    public double getRightCurrent() {
        return right1.getOutputCurrent();
    }

    @Override
    protected void initDefaultCommand() {
    }

}
