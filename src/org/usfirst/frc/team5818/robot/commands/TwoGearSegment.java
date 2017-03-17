package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearSegment extends CommandGroup {

    private double maxPower;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    public TwoGearSegment(Direction dir, Side side, AutoExtra extra, double maxPow) {
        maxPower = maxPow;
        drive = new CommandGroup();
        whileDriving = new CommandGroup();

        final double leftRatAdd = 1.0;
        double rat = 1.6;
        double radius;
        double dist1;
        if (side.equals(Side.RIGHT)) {
            radius = rat;
            dist1 = 30;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / (rat + leftRatAdd);
            dist1 = 30;
        } else {
            radius = 1.0;
            dist1 = 0;
        }

        if (dir.equals(Direction.FORWARD)) {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(4);
                b.maxPower(maxPower);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(58);
                b.maxPower(maxPower);
                b.maxRatio(3.5);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(12);
                b.maxPower((maxPower * 2) / 3);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                if (side == Side.CENTER) {
                    b.inches(0);
                } else {
                    b.inches(26);
                }
                b.maxPower(-maxPower);
                double rat2 = 2;
                if (side == Side.LEFT) {
                    b.targetRatio(rat2 - .5);
                } else {
                    b.targetRatio(1.0 / rat2);
                }
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if (side == Side.CENTER) {
                    b.inches(75);
                } else {
                    b.inches(44);
                }
                b.maxPower(-maxPower);
                b.maxRatio(3);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(5);
                b.maxPower(-maxPower);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        }

        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);

        if (extra == AutoExtra.COLLECT) {
            whileDriving.addSequential((new SetArmAngle(Arm.COLLECT_POSITION)));
            whileDriving.addSequential(new TurretSmallAdjustment(0.0));
            whileDriving.addSequential(new CollectGear(1, 5));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true, 1.0, 1.75));
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
        }

        this.addParallel(drive);
        this.addParallel(whileDriving);
    }

}
