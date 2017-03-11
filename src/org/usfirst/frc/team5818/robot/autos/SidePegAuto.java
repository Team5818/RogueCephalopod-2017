package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SidePegAuto extends CommandGroup {

    public SidePegAuto() {
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(69.55);
            b.maxPower(.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(DriveAtRatio.withSpin(b -> {
            b.angle(60);
            b.rotation(Side.LEFT);
            b.maxPower(.5);
            b.stoppingAtEnd(true);
        }));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(81.0);
            b.maxPower(.5);
            b.maxRatio(3.0);
            b.stoppingAtEnd(false);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(9);
            b.maxPower(.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(new PlaceWithLimit());
    }
}
