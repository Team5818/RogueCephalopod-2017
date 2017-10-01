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

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Routine to find the reflective tape target by spinning!
 */
public class FindTarget extends CommandGroup {

	Command visionSpinArea;
	private final VisionTracker vision = Robot.runningRobot.vision;

	public FindTarget(Spin s, double ang) {
		double mult = 1;
		if (s == Spin.COUNTERCW) {
			mult = -1;
		}
		addSequential(new TapeMode());
		addSequential(new SpinWithProfile(mult * Math.toRadians(ang), true, true));
		addSequential(visionSpinArea = DriveAtRatio.withSpin(b -> {
			b.angle(50);
			b.rotation(s);
			b.maxPower(.2);
			b.stoppingAtEnd(false);
		}));
	}

	@Override
	protected boolean isFinished() {
		if (visionSpinArea.isRunning()) {
			double a = vision.getCurrentAngle();
			if (!Double.isNaN(a) && Math.abs(a) < 4) {
				return true;
			}
		}
		return super.isFinished();
	}

	@Override
	protected void end() {
		Robot.runningRobot.driveTrain.stop();
	}
}
