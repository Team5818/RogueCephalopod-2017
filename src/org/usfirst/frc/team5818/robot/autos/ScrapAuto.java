package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.SpinUntilTargetInFOV;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.SpinWithVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class ScrapAuto extends CommandGroup {

    public ScrapAuto() {
//        addSequential(new SpinWithVision(60, 20, Spin.CLOCKWISE, Camera.CAM_TAPE));
//        addSequential(DriveAtRatio.withSpin(b -> {
//            b.angle(5);
//            b.maxPower(.7);
//            b.rotation(Spin.CLOCKWISE);
//            b.stoppingAtEnd(true);
//        }));
        addSequential(new TapeMode());
        addSequential(new SpinWithProfile(Math.toRadians(40.0), true, true));
        addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));
    }
}
