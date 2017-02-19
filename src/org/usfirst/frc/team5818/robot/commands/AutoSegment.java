package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSegment extends CommandGroup {

    private CommandGroup drive;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

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
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(dist1);
                b.maxPower(-0.4);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_BACKWARD, b -> {
                b.inches(33);
                b.maxPower(-0.4);
                b.maxRatio(4.2);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(7);
                b.maxPower(-0.4);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(dist1);
                b.maxPower(0.4);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_FORWARD, b -> {
                b.inches(31);
                b.maxPower(0.4);
                b.maxRatio(2.6);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(6);
                b.maxPower(0.4);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        }
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);

        this.addSequential(drive);
    }

}
