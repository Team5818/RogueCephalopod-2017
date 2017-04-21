package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.FindTarget;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.SpinWithVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class ProfileSideGear extends CommandGroup{
    
    int angleMult;
    
    public ProfileSideGear(Side turnSide){
        Spin s;
        if(turnSide == Side.LEFT){
            s = Spin.COUNTERCW;
            angleMult = -1;
            
        }
        else{
            s = Spin.CLOCKWISE;
            angleMult = 1;
        }
        addSequential(new TapeMode());
        addSequential(new DriveTrajectory(72, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new FindTarget(s, 35));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(74);
            b.maxPower(.7);
            b.maxRatio(3.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(5);
            b.maxPower(.7);
            b.targetRatio(1);
            b.stoppingAtEnd(true);
        }));
        this.addSequential(new TimedCommand(.5));
        addSequential(new PlaceWithLimit());
        addSequential(new DriveTrajectory(28, angleMult*Math.toRadians(60.0), 0.0, 0.0, Direction.FORWARD, true));
        addSequential(new SpinWithProfile(angleMult*Math.PI, true, false));
        addSequential(new DriveTrajectory(320, angleMult*Math.PI, 0.0, 0.0, Direction.FORWARD, true));
    }
}
