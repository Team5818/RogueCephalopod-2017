package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class AutoSegment extends CommandGroup {

    private double maxPower;
    private CommandGroup approach;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private CommandGroup atEnd;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    public AutoSegment(Direction dir, Side side, AutoExtra extra, double maxPow) {
        maxPower = maxPow;
        approach = new CommandGroup();
        drive = new CommandGroup();
        whileDriving = new CommandGroup();
        atEnd = new CommandGroup();

        double radius;
        double dist1;
        if (side.equals(Side.RIGHT)) {
            radius = 1.6;
            dist1 = 30;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / 1.6;
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
                b.maxRatio(2.0);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(7);
                b.maxPower(maxPower);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                if (side == Side.CENTER) {
                    b.inches(0);
                } else {
                    b.inches(24);
                }
                b.maxPower(-maxPower);
                if (side == Side.LEFT) {
                    b.targetRatio(1.3);
                } else {
                    b.targetRatio(1.0 / 1.2);
                }
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if (side == Side.CENTER) {
                    b.inches(69);
                } else {
                    b.inches(39);
                }
                b.maxPower(-maxPower);
                b.maxRatio(2.0);
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
            whileDriving.addSequential(new TurretReZero());
            whileDriving.addSequential((new SetCollectorAngle(Collector.COLLECT_POSITION)));
            whileDriving.addSequential(new CollectGear(.5, 1));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetCollectorAngle(Collector.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true));
            atEnd.addSequential(new TimedCommand(0.5));
            atEnd.addSequential(new PlaceWithLimit());
        }

        approach.addParallel(drive);
        approach.addParallel(whileDriving);

        this.addSequential(approach);
        this.addSequential(atEnd);
    }

}
