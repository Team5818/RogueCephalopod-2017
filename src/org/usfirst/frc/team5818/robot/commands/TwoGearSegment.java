package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class TwoGearSegment extends CommandGroup {

    private double maxPower;
    private CommandGroup drive;
    private CommandGroup whileDriving;
    private Command driveOvershoot;
    private DriveAtRatio driveVision;
    private DriveAtRatio driveFinal;

    public TwoGearSegment(Direction dir, Side side, AutoExtra extra, double maxPow) {
        maxPower = maxPow;
        drive = new CommandGroup();
        whileDriving = new CommandGroup();

        final double leftRatAdd = 1.0;
        double rat = 1.6;
        double radius;
        if (side.equals(Side.RIGHT)) {
            radius = rat + .5;
        } else if (side.equals(Side.LEFT)) {
            radius = 1.0 / (rat + leftRatAdd);
        } else {
            radius = 1.0;
        }

        if (dir.equals(Direction.FORWARD)) {
            driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                b.inches(12);
                b.maxPower(maxPower);
                b.targetRatio(radius);
                b.stoppingAtEnd(false);
            });
            driveVision = DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
                b.inches(56);
                b.maxPower(maxPower);
                b.maxRatio(3.5);
                b.stoppingAtEnd(false);
            });
            driveFinal = DriveAtRatio.withDeadReckon(b -> {
                b.inches(6);
                b.maxPower((maxPower * 2) / 3);
                b.targetRatio(1);
                b.stoppingAtEnd(true);
            });
        } else {
            if (side != Side.CENTER) {
                driveOvershoot = DriveAtRatio.withDeadReckon(b -> {
                    b.inches(28);
                    b.maxPower(-maxPower);
                    double rat2 = 2;
                    if (side == Side.LEFT) {
                        b.targetRatio(rat2 - .6);
                    } else {
                        b.targetRatio(1.0 / rat2);
                    }
                    b.stoppingAtEnd(false);
                });
            } else {
                driveOvershoot = new InstantCommand();
            }
            driveVision = DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
                if (side == Side.CENTER) {
                    b.inches(75);
                } else {
                    b.inches(44);
                }
                b.maxPower(-maxPower);
                b.maxRatio(5);
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
            whileDriving.addSequential(new SetTurretAngle(Turret.TURRET_CENTER_POS));
            whileDriving.addSequential(new CollectGear(.75, 5));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
//            whileDriving.addSequential(new SetTurretAngle(0));
            whileDriving.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true, 1.0, 1.0));
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
        }

        this.addParallel(drive);
        this.addParallel(whileDriving);
    }

}
