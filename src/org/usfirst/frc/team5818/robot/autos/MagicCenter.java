package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.MagicSpin;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MagicCenter extends CommandGroup{
    
    public MagicCenter() {
        this.addSequential(new MagicDrive(-80.0, 300));
        this.addSequential(new MagicSpin(0.0, 200));
        this.addSequential(new PlaceWithLimit());
    }
    
}
