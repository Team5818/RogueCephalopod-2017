package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScrapAuto extends CommandGroup {

    public ScrapAuto(double angle) {
        addSequential(DriveAtRatio.withSpin(b -> {
            b.maxPower(.55);
            b.angle(angle);
            b.rotation(Spin.CLOCKWISE);
            b.stoppingAtEnd(true);
        }));
        addSequential(new TapeMode());
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(40);
            b.maxPower(.9);
            b.maxRatio(3);
            b.stoppingAtEnd(true);
        }));
    }
}
