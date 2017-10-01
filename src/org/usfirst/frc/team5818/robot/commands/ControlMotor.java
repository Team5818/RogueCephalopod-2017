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
package org.usfirst.frc.team5818.robot.commands;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Routine to directly control a motor from a single stick axis. Used in testing
 * to verify all systems are working.
 */
public class ControlMotor extends Command {

	private static final class CMSystem extends Subsystem {

		private static final CMSystem INSTANCE = new CMSystem();

		@Override
		protected void initDefaultCommand() {
		}
	}

	private final DoubleSupplier stick;
	private final PIDOutput out;

	public ControlMotor(DoubleSupplier stick, PIDOutput out) {
		this.stick = stick;
		this.out = out;
		requires(CMSystem.INSTANCE);
	}

	@Override
	protected void initialize() {
		if (out instanceof CANTalon) {
			SmartDashboard.putString("ControlMotor", "CM on for " + ((CANTalon) out).getDeviceID());
		}
	}

	@Override
	protected void execute() {
		out.pidWrite(MathUtil.adjustDeadband(new Vector2d(stick.getAsDouble(), 0), Driver.DEADBAND_VEC).getX());
	}

	@Override
	protected void end() {
		out.pidWrite(0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
