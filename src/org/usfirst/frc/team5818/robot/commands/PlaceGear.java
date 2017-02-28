package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PlaceGear extends CommandGroup {

    public PlaceGear() {
        this.addSequential(new SetExtendTurret(true));
        this.addSequential(new SetPunchTurret(true));
        this.addSequential(new TimedCommand(0.2));
        this.addSequential(new SetExtendTurret(false));
        this.addSequential(new SetPunchTurret(false));
    }

}
