package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class LightsOn extends Command{
    
    private VisionTracker vis;
    private boolean on;
    
    public LightsOn(boolean b) {
        vis = Robot.runningRobot.vision;
        on = b;
    }
    
    @Override
    protected void initialize() {
        vis.setLightsOn(on);
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
