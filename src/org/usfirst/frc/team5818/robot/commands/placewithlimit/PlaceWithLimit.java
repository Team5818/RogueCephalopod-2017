package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PlaceWithLimit extends CommandGroup {

    public PlaceWithLimit() {
        this.setInterruptible(false);
        addSequential(new DiscoverPlacePosition());
        // this.addSequential(new ConditionalCommand(new PlaceGearLimit(), new
        // QuickPlace()) {
        //
        // @Override
        // protected boolean condition() {
        // return Math.abs(Robot.runningRobot.turret.getAngle()) < 45;
        // }
        // });
        addSequential(new PlaceGearLimit());
    }

}
