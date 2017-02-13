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
            driveOvershoot = new DriveStraight(24, -.4, radius, false);
            driveVision = new DriveStraight(36, -.4, 2.0, DriveStraight.Camera.CAM_BACKWARD, false);
            driveFinal = new DriveStraight(12, -.4, 1.0, true);
        }
        else{
            driveOvershoot = new DriveStraight(24, .4, radius, false);
            driveVision = new DriveStraight(36, .4, 2.0, DriveStraight.Camera.CAM_FORWARD, false);
            driveFinal = new DriveStraight(12, .4, 1.0, true);
        }
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);
        
        this.addSequential(drive);
    }

}
