package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSegment extends CommandGroup{

    
    private DriveStraight overshoot;
    private DriveStraight visionCorrection;
    private DriveStraight finalStretch;
    
    public enum Direction{
        BACKWARD, FORWARD
    }
    
    public AutoSegment(Direction dir){
        if(dir.equals(Direction.BACKWARD)){
            overshoot = new DriveStraight(24, -.4, 1.8, false);
            visionCorrection = new DriveStraight(36, -.4, 2.0, DriveStraight.Camera.CAM_BACKWARD, false);
            finalStretch = new DriveStraight(12, -.4, 1.0, true);
        }
        else{
            overshoot = new DriveStraight(24, .4, 1.8, false);
            visionCorrection = new DriveStraight(36, .4, 2.0, DriveStraight.Camera.CAM_FORWARD, false);
            finalStretch = new DriveStraight(12, .4, 1.0, true);
        }
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

}
