package org.usfirst.frc.team5818.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5818.robot.Robot;


public class DeployGear extends Command {
    
    private Turret turr;
    
    enum Position{
        RETRACT, HALF_EXTEND, EXTEND
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
                turr.setLeftDeploy(false);
                turr.setRightDeploy(false);
                break;
            case HALF_EXTEND:
                turr.setLeftDeploy(true);
                turr.setRightDeploy(false);
                break;
            case EXTEND:
                turr.setLeftDeploy(true);
                turr.setRightDeploy(true);
                break;
        }
        
        done = true;
    }
    
    @Override
    protected boolean isFinished() {
        return done;
    }
}
