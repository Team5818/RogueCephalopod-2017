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
    /*
     * @param radius > 1 means arc right, radius < 1 means arc left. Same for forward or backward.
     */
    public AutoSegment(Direction dir, double radius){
        drive = new CommandGroup();
        if(dir.equals(Direction.BACKWARD)){
            driveOvershoot = new DriveStraight(30, -.4, radius, false);
            driveVision = new DriveStraight(30, -.4, 3.2, DriveStraight.Camera.CAM_BACKWARD, false);
            driveFinal = new DriveStraight(8, -.4, 1.0, true);
        }
        else{
            driveOvershoot = new DriveStraight(30, .4, radius, false);
            driveVision = new DriveStraight(30, .4, 2.6, DriveStraight.Camera.CAM_FORWARD, false);
            driveFinal = new DriveStraight(8, .4, 1.0, true);
        }
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);
        
        this.addSequential(drive);
    }

}
