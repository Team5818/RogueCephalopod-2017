package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class MagicOneGearCenterAuto extends CommandGroup{

	private MagicDrive centerDrive;
	private PlaceWithLimit place;
	
	 public MagicOneGearCenterAuto() {
	        setInterruptible(false);
	        centerDrive = new MagicDrive(60); // figure out distance
	        place = new PlaceWithLimit();
	        this.addSequential(centerDrive);
	        this.addSequential(new TimedCommand(1.0));
	        this.addSequential(place);
	 }
}
