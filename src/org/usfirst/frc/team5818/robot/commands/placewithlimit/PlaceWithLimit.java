package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.QuickPlace;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class PlaceWithLimit extends CommandGroup {

    private DiscoverPlacePosition discoverPlacePosition = new DiscoverPlacePosition();

    public PlaceWithLimit() {
        this.setInterruptible(false);
        this.addSequential(discoverPlacePosition);
        this.addSequential(new ConditionalCommand(new PlaceGearLimit(), new QuickPlace()) {

            @Override
            protected boolean condition() {
                return Math.abs(Robot.runningRobot.turret.getAngle()) < 45;
            }
        });
    }
    
    @Override
    protected boolean isFinished() {
        boolean fin = super.isFinished();
        return fin;
    }

}
