package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import edu.wpi.first.wpilibj.command.Command;

public class SwitchExposure extends Command{

    private CameraController cont;
    private boolean done;
    
    public SwitchExposure(){
        cont = Robot.runningrobot.camCont;
        done = false;
    }
    
    @Override
    protected void initialize(){
        cont.switchExposure();
        done = true;
    }
    
    @Override
    protected boolean isFinished() {
        return done;
    }

}