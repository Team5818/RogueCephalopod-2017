package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveStraight;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveAuto extends CommandGroup{
    DriveStraight drive;
    
    public DriveAuto() {
        drive = new DriveStraight(120);
        this.addSequential(new TimedCommand(1.0));
        this.addSequential(drive);
    }
    
}
