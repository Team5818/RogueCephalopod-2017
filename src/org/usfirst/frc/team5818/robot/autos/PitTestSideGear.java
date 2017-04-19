package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PitTestSideGear extends CommandGroup{
        
    public PitTestSideGear(){
        addSequential(new DriveTrajectory(50, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new SpinWithProfile(Math.PI, true, false));
        addSequential(new DriveTrajectory(50, Math.PI, 0.0, 0.0, Direction.BACKWARD, true));
        this.addSequential(new TimedCommand(1.0));
        addSequential(new PlaceWithLimit());
    }
}
