package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DoNothing extends Command{

    @Override
    public void initialize() {
        
    }
    
    @Override
    protected void execute() {
        
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
