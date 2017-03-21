package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class ProfileSideGear extends CommandGroup{
    
    int angleMult;
    
    public ProfileSideGear(Side turnSide){
        if(turnSide == Side.LEFT){
            angleMult = -1;
        }
        else{
            angleMult = 1;
        }
        addSequential(new DriveTrajectory(70, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new SpinWithProfile(angleMult*Math.toRadians(60.0), true, false));
        addSequential(new DriveTrajectory(36, angleMult*Math.toRadians(60.0), 0.0, 0.0, Direction.BACKWARD, true));
//        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
//            b.inches(28);
//            b.maxPower(.7);
//            b.maxRatio(3.0);
//            b.stoppingAtEnd(false);
//        }));
//        addSequential(DriveAtRatio.withDeadReckon(b -> {
//            b.inches(8);
//            b.maxPower(.7);
//            b.targetRatio(1.0);
//            b.stoppingAtEnd(true);
//        }));
        this.addSequential(new TimedCommand(1.0));
        addSequential(new PlaceWithLimit());
        addSequential(new DriveTrajectory(36, angleMult*Math.toRadians(60.0), 0.0, 0.0, Direction.FORWARD, true));
        addSequential(new SpinWithProfile(Math.PI, true, false));
        addSequential(new DriveTrajectory(70, Math.PI, 0.0, 0.0, Direction.FORWARD, true));
    }
}
