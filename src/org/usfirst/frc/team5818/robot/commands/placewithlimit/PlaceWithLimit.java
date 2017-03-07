package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceWithLimit extends CommandGroup {

    public PlaceWithLimit() {
        this.addSequential(new DiscoverPlacePosition());
        this.addSequential(new PlaceGearLimit());
    }

}
