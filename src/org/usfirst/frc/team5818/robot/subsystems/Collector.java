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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Simple subsystem for collector. Has a top and bottom roller, gear gets sucked
 * in between rollers
 */
public class Collector extends Subsystem {

    private CANTalon topRoller;
    private CANTalon botRoller;
    private DigitalInput limitSwitch;

    public Collector() {
        topRoller = new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER);
        botRoller = new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER);
        limitSwitch = new DigitalInput(RobotMap.COLLECTOR_LIMIT_SWITCH);
    }

    public void setTopPower(double x) {
        topRoller.set(x);
    }

    public void setBotPower(double x) {
        botRoller.set(x);
    }

    public void stop() {
        topRoller.set(0);
        botRoller.set(0);
    }

    public double getTopCurrent() {
        return topRoller.getOutputCurrent();
    }

    public double getBotCurrent() {
        return botRoller.getOutputCurrent();
    }

    /* Limit switch indicates when gear is collected */
    public boolean isLimitTriggered() {
        return !limitSwitch.get();
    }

    @Override
    protected void initDefaultCommand() {

    }

}
