package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PitTestSideGear extends CommandGroup{
        
    
    int angleMult;
    
    public PitTestSideGear(Side turnSide){
        if(turnSide == Side.LEFT){
            angleMult = -1;
        }
        else{
            angleMult = 1;
        }
        addSequential(new TapeMode());
        addSequential(new DriveTrajectory(24, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new SpinWithProfile(angleMult*Math.toRadians(160.0), true, true));
        addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(24);
            b.maxPower(.7);
            b.maxRatio(3.0);
            b.stoppingAtEnd(true);
        }));
        this.addSequential(new TimedCommand(.5));
        addSequential(new PlaceWithLimit());
        addSequential(new DriveTrajectory(28, 0.0, 0.0, Direction.FORWARD, true));
        addSequential(new SpinWithProfile(angleMult*Math.PI, true, false));
        addSequential(new DriveTrajectory(320, angleMult*Math.PI, 0.0, 0.0, Direction.FORWARD, true));
    }
}
