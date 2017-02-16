package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class ExposureHigh extends Command{

    private CameraController cont;
    private VisionTracker track;
    private boolean done;
    
    public ExposureHigh(){
        cont = Robot.runningrobot.camCont;
        track = Robot.runningrobot.track;
        done = false;
    }
    
    @Override
    protected void initialize(){
        cont.exposureHigh();
        done = true;
    }
    
    @Override
    protected boolean isFinished() {
        return done;
    }

}