package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{

    private long targetTime;
    private long startTime;
    
    public DoNothing(long delayTimeMillis, double timeout) {
        setTimeout(timeout);
        startTime = System.currentTimeMillis();
        targetTime = delayTimeMillis;
    }
    
    @Override
    public void initialize() {
        
    }
    
    @Override
    protected void execute() {
        
    }
    
    @Override
    protected boolean isFinished() {
        return (System.currentTimeMillis()-startTime) >= targetTime;
    }
    
    @Override
    protected void interrupted() {
        DriverStation.reportError("Stopped stopping.", false);
        end();
    }

}
