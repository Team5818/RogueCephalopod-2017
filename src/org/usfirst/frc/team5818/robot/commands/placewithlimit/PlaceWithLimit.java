package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
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
        double angle = Robot.runningRobot.turret.getAngle();
        if (Math.abs(angle) < 45) {
            new PlaceGearLimit().start();
        } else {
            new QuickPlace().start();
        }
    }

}
