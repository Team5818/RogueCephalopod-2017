package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendTurret extends Command{
    private Turret turr;
    private boolean on;
    
    public ExtendTurret(boolean b){
        turr = Robot.runningRobot.turret;
        on = b;
    }
    
    @Override 
    protected void initialize(){
        turr.extend(on);
    }
    
    @Override
    protected boolean isFinished(){
        return true;
    }
}
