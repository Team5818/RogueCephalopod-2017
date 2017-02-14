package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{

    public DoNothing(double timeout) {
        setTimeout(timeout);
    }
    
    @Override
    public void initialize() {
        
    }
    
    @Override
    protected void execute() {
        /*
         * Insert limerick here.
         * 
         */
    }
    
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
    
    @Override
    protected void interrupted() {
        DriverStation.reportError("Stopped stopping.", false);
        end();
    }

}
