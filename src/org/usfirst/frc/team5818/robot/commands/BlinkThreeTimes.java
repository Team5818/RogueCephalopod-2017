package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class BlinkThreeTimes extends CommandGroup{

    public BlinkThreeTimes() {
        this.addSequential(new LightsOn(true));
        this.addSequential(new TimedCommand(.07));
        this.addSequential(new LightsOn(false));
        this.addSequential(new TimedCommand(.04));        
        this.addSequential(new LightsOn(true));
        this.addSequential(new TimedCommand(.07));
        this.addSequential(new LightsOn(false));
        this.addSequential(new TimedCommand(.07));
        this.addSequential(new LightsOn(true));
        this.addSequential(new TimedCommand(.07));
        this.addSequential(new LightsOn(true));
        this.addSequential(new TimedCommand(.04));
        this.addSequential(new LightsOn(false));
    }
}
