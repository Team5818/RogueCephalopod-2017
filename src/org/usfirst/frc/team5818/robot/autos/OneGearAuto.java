package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.StartingPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OneGearAuto extends CommandGroup {

    private static final double INITIAL_DRIVE_DISTANCE = 5;
    private static final double INITIAL_DRIVE_POWER = 0.5;
    private static final double INITIAL_DRIVE_RATIO = 0.85;
    private static final double VISION_DISTANCE = 5;
    private static final double PEG_PLACE_DISTANCE = 5;
    private static final double PEG_PLACE_POWER = 0.3;

    public OneGearAuto(StartingPosition pos) {
        switch (pos) {
            case LEFT:
                addCurve(1);
                break;
            case RIGHT:
                addCurve(-1);
                break;
            case CENTER:
                break;
            default:
                throw new IllegalArgumentException(pos + " is an unknown starting position");
        }
        addSequential(DriveAtRatio.withVision(Camera.CAM_FORWARD, b -> {
            b.inches(VISION_DISTANCE);
            b.maxPower(0.3);
            b.maxRatio(1);
            b.stoppingAtEnd(false);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(PEG_PLACE_DISTANCE);
            b.maxPower(PEG_PLACE_POWER);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
    }

    private void addCurve(int sideMultiplier) {
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(INITIAL_DRIVE_DISTANCE);
            b.maxPower(INITIAL_DRIVE_POWER);
            b.targetRatio(sideMultiplier * INITIAL_DRIVE_RATIO);
            b.stoppingAtEnd(false);
        }));
    }

}
