package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class TwoGearSegment extends CommandGroup {

    private double maxPower;
    private CommandGroup approach;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private CommandGroup atEnd;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    public TwoGearSegment(Direction dir, Side side, AutoExtra extra, double maxPow) {
        maxPower = maxPow;
        approach = new CommandGroup();
        drive = new CommandGroup();
        whileDriving = new CommandGroup();
        atEnd = new CommandGroup();

        double radius;
        double dist1;
        if (side.equals(Side.RIGHT)) {
            radius = 1.4;
            dist1 = 30;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / 1.4;
            dist1 = 30;
        } else {
            radius = 1.0;
            dist1 = 0;
        }

        if (dir.equals(Direction.FORWARD)) {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(18);
                b.maxPower(maxPower);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(39);
                b.maxPower(maxPower);
                b.maxRatio(2);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(15);
                b.maxPower((maxPower * 2) / 3);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                if (side == Side.CENTER) {
                    b.inches(0);
                } else {
                    b.inches(16);
                }
                b.maxPower(-maxPower);
                if (side == Side.LEFT) {
                    b.targetRatio(1.4);
                } else {
                    b.targetRatio(1.0 / 1.4);
                }
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if (side == Side.CENTER) {
                    b.inches(75);
                } else {
                    b.inches(47);
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
            whileDriving.addSequential(new TurretSmallAdjustment(0.0));
            whileDriving.addSequential((new SetArmAngle(Arm.COLLECT_POSITION)));
            whileDriving.addSequential(new CollectGear(.5, 1));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true));
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
            atEnd.addSequential(new TimedCommand(1.0));
            atEnd.addSequential(new PlaceWithLimit());
        }

        approach.addParallel(drive);
        approach.addParallel(whileDriving);

        this.addSequential(approach);
        this.addSequential(atEnd);
    }

}
