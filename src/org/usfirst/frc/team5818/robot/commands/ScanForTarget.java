package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.constants.Camera;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScanForTarget extends CommandGroup{
    
    public ScanForTarget(Camera cam, double ang){
        this.addSequential(new SpinUntilTargetInFOV(ang));
        //this.addSequential(new SpinWithProfileVision(true, cam));

    }
    
}
