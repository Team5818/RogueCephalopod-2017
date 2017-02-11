package org.usfirst.frc.team5818.robot.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class DoNothing extends TimedCommand {

    public DoNothing(long time, TimeUnit unit) {
        super(unit.toSeconds(time));
    }

}
