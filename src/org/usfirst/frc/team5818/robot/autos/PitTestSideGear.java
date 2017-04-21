package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.FindTarget;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class PitTestSideGear extends CommandGroup{
        
    
    int angleMult;
    
    public PitTestSideGear(Side turnSide){
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
        addSequential(new DriveTrajectory(36, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        addSequential(new FindTarget(s, 120));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(36);
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
