package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.QuickPlace;
import org.usfirst.frc.team5818.robot.commands.dpp.DransPacificPartnership;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class PlaceWithLimit extends CommandGroup {

    private Command discoverPlacePosition = new DransPacificPartnership();

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
