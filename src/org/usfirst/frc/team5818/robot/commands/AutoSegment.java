package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSegment extends CommandGroup{

    private CommandGroup drive;
    private DriveStraight driveOvershoot;
    private DriveStraight driveVision;
    private DriveStraight driveFinal;
    
    public enum Direction{
        BACKWARD, FORWARD
    }
    
    public enum Side{
        LEFT, RIGHT, STRAIGHT
    }
    /*
     * @param radius > 1 means arc right, radius < 1 means arc left. Same for forward or backward.
     */
    public AutoSegment(Direction dir, Side side){
        drive = new CommandGroup();
        double radius;
        double dist1;
        if(side.equals(Side.RIGHT)){
            radius = 1.8;
            dist1 = 30;
        }
        else if(side.equals(Side.LEFT)){
            radius = 1.0/1.8;
            dist1 = 30;
        }
        else{
            radius = 1.0;
            dist1 = 27.5;
        }
        
        if(dir.equals(Direction.BACKWARD)){
            driveOvershoot = new DriveStraight(dist1, -.4, radius, false);
            driveVision = new DriveStraight(30, -.4, 3.6, DriveStraight.Camera.CAM_BACKWARD, false);
            driveFinal = new DriveStraight(8, -.4, 1.0, true);
        }
        else{
            driveOvershoot = new DriveStraight(dist1, .4, radius, false);
            driveVision = new DriveStraight(30, .4, 2.6, DriveStraight.Camera.CAM_FORWARD, false);
            driveFinal = new DriveStraight(7, .4, 1.0, true);
        }
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);
        
        this.addSequential(drive);
    }

}
