package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeMini extends Command{
    
    private Turret turr;
    private Side side;
    
    public ChangeMini(Side s){
        turr = Robot.runningRobot.turret;
        side = s;
        requires(turr);
    }
    
    @Override
    protected void initialize(){
        turr.switchCurrentMini(side);;
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

    
    
}
