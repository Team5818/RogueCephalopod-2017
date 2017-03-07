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
    private double maxPower;
    private double finalMaxPower;
    private CommandGroup approach;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private CommandGroup atEnd;
    private DriveAtRatio driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;
    private double visionOffset;

    public AutoSegment(Direction dir, Side side, AutoExtra extra, double maxPow, double finalMaxPow) {
        maxPower = maxPow;
        finalMaxPower = finalMaxPow;
        approach = new CommandGroup();
        drive = new CommandGroup();
        whileDriving = new CommandGroup();
        atEnd = new CommandGroup();
        visionOffset = 10;

        if (dir.equals(Direction.FORWARD)) {
            driveOvershoot = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(30);
                b.visionOffset(visionOffset);
                b.maxPower(maxPower);
                b.maxRatio(1.7);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(30);
                b.visionOffset(0.0);
                b.maxPower(maxPower);
                b.maxRatio(1.7);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(7);
                b.maxPower(finalMaxPower);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            driveOvershoot = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if(side == Side.CENTER){
                    b.inches(0);
                }else{
                    b.inches(30);
                }
                b.maxPower(-maxPower);
                b.maxRatio(1.7);
                b.visionOffset(visionOffset);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if(side == Side.CENTER){
                    b.inches(69);
                }else{
                    b.inches(31);
                }
                b.maxPower(-maxPower);
                b.maxRatio(1.7);
                b.visionOffset(0.0);
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
            whileDriving.addSequential((new SetCollectorAngle(Collector.COLLECT_POSITION)));
            whileDriving.addSequential(new CollectGear(.5, 1));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetCollectorAngle(Collector.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true));
            atEnd.addSequential(new PlaceWithLimit());
        }

        approach.addParallel(drive);
        approach.addParallel(whileDriving);
        
        this.addSequential(approach);
        this.addSequential(atEnd);
    }

}
