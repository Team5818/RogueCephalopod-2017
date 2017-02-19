package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveStraight;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.StartingPosition;
import org.usfirst.frc.team5818.robot.subsystems.CameraController.Camera;

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
                throw new IllegalArgumentException(
                        pos + " is an unknown starting position");
        }
        addSequential(new DriveStraight(VISION_DISTANCE, BotConstants.MAX_POWER,
                1, Camera.CAM_FORWARD, false));
        addSequential(new DriveStraight(PEG_PLACE_DISTANCE, PEG_PLACE_POWER,
                1.0, true));
    }

    private void addCurve(int sideMultiplier) {
        addSequential(
                new DriveStraight(INITIAL_DRIVE_DISTANCE, INITIAL_DRIVE_POWER,
                        sideMultiplier * INITIAL_DRIVE_RATIO, false));
    }

}
