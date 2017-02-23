package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.MovePiston.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PlaceGear extends CommandGroup {

    public PlaceGear() {
        this.addSequential(new MovePiston(Position.PLACE));
        this.addSequential(new TimedCommand(0.2));
        this.addSequential(new MovePiston(Position.RETRACT));
    }

}
