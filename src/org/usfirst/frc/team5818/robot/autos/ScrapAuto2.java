package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScrapAuto2 extends CommandGroup {

    Command visionSpinArea;
    private final VisionTracker vision = Robot.runningRobot.vision;
    
    public ScrapAuto2() {
//        addSequential(new SpinWithVision(60, 20, Spin.CLOCKWISE, Camera.CAM_TAPE));
//        addSequential(DriveAtRatio.withSpin(b -> {
//            b.angle(5);
//            b.maxPower(.7);
//            b.rotation(Spin.CLOCKWISE);
//            b.stoppingAtEnd(true);
//        }));
        addSequential(new TapeMode());
        addSequential(new SpinWithProfile(Math.toRadians(40.0), true, true));
        addSequential(visionSpinArea = DriveAtRatio.withSpin(b -> {
            b.angle(20);
            b.rotation(Spin.CLOCKWISE);
            b.maxPower(.5);
            b.stoppingAtEnd(false);
        }));
        //addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));
        //addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));    
    }
    
    @Override
    protected boolean isFinished() {
        if (visionSpinArea.isRunning()) {
            double a = vision.getCurrentAngle();
            if (!Double.isNaN(a) && Math.abs(a) < 5) {
                return true;
            }
        }
        return super.isFinished();
    }
}
