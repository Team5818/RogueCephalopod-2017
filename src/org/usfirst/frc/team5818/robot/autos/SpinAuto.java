package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicSpin;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class SpinAuto extends CommandGroup {
	private MagicSpin spin;
	
	public SpinAuto(double ang) {
		this.setInterruptible(false);
		spin = new MagicSpin(ang);
		this.addSequential(spin);
	}

}
