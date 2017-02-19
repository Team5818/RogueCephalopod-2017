package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{

    @Override
    public void initialize() {
        
    }
    
    @Override
    protected void execute() {
        /*
         * There once was a man from Nantucket.
         * He brought money around in a bucket.
         * One day he fell,
         * it went down the well,
         * so then he yelled "OH F*** IT!"
         */
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void interrupted() {
        this.end();
    }

}
