package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.constants.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestProfileCurves extends CommandGroup{
    
    public TestProfileCurves(){
        addSequential(new DriveTrajectory(70, Math.toRadians(-60.0), 0.0, 0.0, Direction.FORWARD, true));
        addSequential(new DriveTrajectory(70, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
    }
    
}
