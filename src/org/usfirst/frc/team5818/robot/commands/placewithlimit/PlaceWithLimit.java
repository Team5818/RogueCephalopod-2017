package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.QuickPlace;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PlaceWithLimit extends CommandGroup {

    private DiscoverPlacePosition discoverPlacePosition = new DiscoverPlacePosition();

    public PlaceWithLimit() {
        setTimeout(3);
        this.setInterruptible(false);
        this.addSequential(discoverPlacePosition);
        this.addSequential(new ConditionalCommand(new PlaceGearLimit(), new QuickPlace()) {

            @Override
            protected boolean condition() {
                return Math.abs(Robot.runningRobot.turret.getAngle()) < 45;
            }
        });
        this.addSequential(new InstantCommand() {

            @Override
            protected void initialize() {
                SmartDashboard.putString("PWLEnd", "Ended command chains.");
            }
        });
    }
    
    @Override
    protected boolean isFinished() {
        return super.isFinished() || isTimedOut();
    }

    @Override
    protected void end() {
        SmartDashboard.putString("PWLEnd", "it ended");
    }

    @Override
    protected void interrupted() {
        SmartDashboard.putString("PWLEnd", "it interrupted");
    }

}
