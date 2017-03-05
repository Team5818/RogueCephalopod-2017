package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSegment extends CommandGroup {
    
    public enum AutoExtra {
        COLLECT, PLACE
    }
    private CommandGroup approach;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private CommandGroup atEnd;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    public AutoSegment(Direction dir, Side side, AutoExtra extra) {
        approach = new CommandGroup();
        drive = new CommandGroup();
        whileDriving = new CommandGroup();
        atEnd = new CommandGroup();

        double radius;
        double dist1;
        if (side.equals(Side.RIGHT)) {
            radius = 5;
            dist1 = 30;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / 5;
            dist1 = 30;
        } else {
            radius = 1.0;
            dist1 = 0;
        }

        if (dir.equals(Direction.FORWARD)) {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(15);
                b.maxPower(0.4);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(45);
                b.maxPower(0.4);
                b.maxRatio(4.2);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(7);
                b.maxPower(0.4);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                if(side == Side.CENTER){
                    b.inches(0);
                }else{
                    b.inches(34);
                }
                b.maxPower(-0.4);
                b.targetRatio(1.0);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if(side == Side.CENTER){
                    b.inches(69);
                }else{
                    b.inches(27);
                }
                b.maxPower(-0.4);
                b.maxRatio(4.2);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(5);
                b.maxPower(-0.4);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        }
        
        drive.addSequential(driveOvershoot);
        drive.addSequential(driveVision);
        drive.addSequential(driveFinal);
        
        if (extra == AutoExtra.COLLECT) {
            whileDriving.addSequential((new SetCollectorAngle(Collector.COLLECT_POSITION)));
            whileDriving.addSequential(new CollectGear(.5, 2));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetCollectorAngle(Collector.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true));
            atEnd.addSequential(new PlaceGear());
        }

        approach.addParallel(drive);
        approach.addParallel(whileDriving);
        
        this.addSequential(approach);
        this.addSequential(atEnd);
    }

}
