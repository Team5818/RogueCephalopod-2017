package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;


public class DeployGear extends Command {
    
    private Turret turr;
    
    public enum Position{
        RETRACT, EXTEND, PLACE
    }
    
    private Position target;
    
    private boolean done;
    
    public DeployGear(Position pos) {
        target = pos;
        turr = Robot.runningrobot.turret;
        
        done = false;
    }
    
    public void initialize() {
        switch (target) {
            case RETRACT:
                turr.extend(false);
                turr.punch(false);
                break;
            case EXTEND:
                turr.extend(true);
                turr.punch(false);
                break;
            case PLACE:
                turr.extend(true);
                turr.punch(true);
                break;
        }
        
        done = true;
    }
    
    @Override
    protected boolean isFinished() {
        return done;
    }
}