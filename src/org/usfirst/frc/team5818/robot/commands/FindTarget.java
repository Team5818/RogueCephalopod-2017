package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class FindTarget extends CommandGroup {

    Command visionSpinArea;
    private final VisionTracker vision = Robot.runningRobot.vision;
    
    public FindTarget(Spin s, double ang) {
        double mult = 1;
        if(s == Spin.COUNTERCW){
           mult = -1; 
        }
//        addSequential(new SpinWithVision(60, 20, Spin.CLOCKWISE, Camera.CAM_TAPE));
//        addSequential(DriveAtRatio.withSpin(b -> {
//            b.angle(5);
//            b.maxPower(.7);
//            b.rotation(Spin.CLOCKWISE);
//            b.stoppingAtEnd(true);
//        }));
        addSequential(new TapeMode());
        addSequential(new SpinWithProfile(mult*Math.toRadians(ang), true, true));
        addSequential(visionSpinArea = DriveAtRatio.withSpin(b -> {
            b.angle(50);
            b.rotation(s);
            b.maxPower(.2);
            b.stoppingAtEnd(false);
        }));
        //addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));
        //addSequential(new SpinWithProfileVision(true, Camera.CAM_TAPE));    
    }
    
    @Override
    protected boolean isFinished() {
        if (visionSpinArea.isRunning()) {
            double a = vision.getCurrentAngle();
            if (!Double.isNaN(a) && Math.abs(a) < 4) {
                return true;
            }
        }
        return super.isFinished();
    }
    
    @Override
    protected void end(){
        Robot.runningRobot.driveTrain.stop();
    }
}
