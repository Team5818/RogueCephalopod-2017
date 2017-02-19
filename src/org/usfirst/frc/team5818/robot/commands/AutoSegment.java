package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSegment extends CommandGroup {

    private CommandGroup drive;
    private DriveStraight driveOvershoot;
    private DriveStraight driveVision;
    private DriveStraight driveFinal;

    /*
     * @param radius > 1 means arc right, radius < 1 means arc left. Same for
     * forward or backward.
     */
    public AutoSegment(Direction dir, Side side) {
        drive = new CommandGroup();
        double radius;
        double dist1;
        if (side.equals(Side.RIGHT)) {
            radius = 2.2;
            dist1 = 25;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / 2.2;
            dist1 = 25;
        } else {
            radius = 1.0;
            dist1 = 31;
        }

        if (dir.equals(Direction.BACKWARD)) {
            driveOvershoot = new DriveStraight(dist1, -.4, radius, false);
            driveVision = new DriveStraight(33, -.4, 4.2,
                    Camera.CAM_BACKWARD, false);
            driveFinal = new DriveStraight(7, -.4, 1.0, true);
        } else {
            driveOvershoot = new DriveStraight(dist1, .4, radius, false);
            driveVision = new DriveStraight(31, .4, 2.6,
                    Camera.CAM_FORWARD, false);
            driveFinal = new DriveStraight(6, .4, 1.0, true);
        }
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);

        this.addSequential(drive);
    }

}
