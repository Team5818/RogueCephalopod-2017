package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PlaceGear extends CommandGroup {

    public PlaceGear() {
        this.addSequential(new ExtendTurret(true));
        this.addSequential(new PunchTurret(true));
        this.addSequential(new TimedCommand(0.2));
        this.addSequential(new ExtendTurret(false));
        this.addSequential(new PunchTurret(false));
    }

}
