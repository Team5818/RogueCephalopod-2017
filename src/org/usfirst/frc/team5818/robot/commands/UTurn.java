package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class UTurn extends CommandGroup {
	private static final double BOT_WIDTH = 32.0; // guess
	private DriveAtRatio turn;
	
	public UTurn(double insideRadius, double power, Side side) {
		turn = DriveAtRatio.withDeadReckon(t -> {
			// side == the side that the robot goes to from the perspective of the driver
			if (side == Side.LEFT) {
				t.targetRatio(insideRadius / (BOT_WIDTH + insideRadius));
			} else if (side == Side.RIGHT) {
				t.targetRatio(((BOT_WIDTH + insideRadius) / insideRadius));
			} else {
				throw new IllegalArgumentException("side must be either left or right");
			}
			t.inches(insideRadius + (BOT_WIDTH / 2) * Math.PI);
			t.maxPower(power);
			t.stoppingAtEnd(false);
		});
		this.addSequential(turn);
	}
		
}
