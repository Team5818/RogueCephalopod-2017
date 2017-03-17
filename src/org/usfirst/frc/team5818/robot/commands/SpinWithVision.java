package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class SpinWithVision extends CommandGroup {

    private final VisionTracker vision = Robot.runningRobot.vision;
    private final Command visionSpinArea;

    public SpinWithVision(double angle, double portionVision, Spin spin, Camera camera) {
        final double first = angle - portionVision;
        final double last = portionVision + 10;
        final double pow = .5;
        if (camera == Camera.CAM_TAPE) {
            addSequential(new TapeMode());
        } else if (camera == Camera.CAM_GEARS) {
            addSequential(new GearMode());
        }
        addSequential(DriveAtRatio.withSpin(b -> {
            b.angle(first);
            b.rotation(spin);
            b.maxPower(pow);
            b.stoppingAtEnd(true);
        }));
        addSequential(new TimedCommand(.5));
        addSequential(visionSpinArea = DriveAtRatio.withSpin(b -> {
            b.angle(last);
            b.rotation(spin);
            b.maxPower(pow);
            b.stoppingAtEnd(false);
        }));
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

    @Override
    protected void end() {
        Robot.runningRobot.driveTrain.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
