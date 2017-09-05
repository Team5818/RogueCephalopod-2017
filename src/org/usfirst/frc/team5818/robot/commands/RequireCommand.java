package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RequireCommand extends Command{

    private boolean running = false;
    
    public void require(Subsystem sys){
        require(sys);
        this.start();
    }
    
    public void stop() {
        running = false;
    }
    
    @Override
    protected boolean isFinished() {
        return running;
    }

}
