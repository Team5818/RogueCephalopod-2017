package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.commands.QuickPlace;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceWithLimit extends CommandGroup {

    private DiscoverPlacePosition discoverPlacePosition = new DiscoverPlacePosition();

    public PlaceWithLimit() {
        this.setInterruptible(false);
        this.addSequential(discoverPlacePosition);
    }

    @Override
    protected void end() {
        if (discoverPlacePosition.isCenterPlace()) {
            new PlaceGearLimit().start();
        } else {
            new QuickPlace().start();
        }
    }

}
