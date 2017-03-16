package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class QuickPlace extends CommandGroup {

    public QuickPlace() {
        this.addSequential(new SetExtendTurret(true));
        this.addSequential(new TimedCommand(.2));
        this.addSequential(new SetPunchTurret(true, .01));
        this.addSequential(new TimedCommand(.1));
        this.addSequential(new SetExtendTurret(false));
        this.addSequential(new TimedCommand(.3));
        this.addSequential(new SetPunchTurret(false, .01));
    }

}
