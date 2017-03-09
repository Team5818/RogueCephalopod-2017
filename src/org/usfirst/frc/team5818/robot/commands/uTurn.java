package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class uTurn extends CommandGroup {
	private static final double BOT_WIDTH = 32.0; // guess
	private DriveAtRatio turn;
	
	public uTurn(double insideRadius, double power, Side side) {
		turn = DriveAtRatio.withDeadReckon(t -> {
			if (side == Side.LEFT) {
				t.targetRatio(((BOT_WIDTH + insideRadius) / insideRadius));
			} else {
				t.targetRatio(insideRadius / (BOT_WIDTH + insideRadius));
			}
			t.inches(insideRadius + (BOT_WIDTH / 2) * Math.PI);
			t.maxPower(-1 * power);
			t.stoppingAtEnd(false);
		});
		this.addSequential(turn);
	}
		
}
