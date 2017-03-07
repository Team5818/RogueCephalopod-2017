package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class TurretReZero extends Command{

    private Turret turr = Robot.runningRobot.turret;
    private double startAngle;
    
    @Override
    protected void initialize(){
        startAngle = turr.getAngle(); 
    }
    
    @Override
    protected void execute(){
        turr.setPower(-.4 * Math.signum(startAngle));
    }
    
    @Override
    protected boolean isFinished() {
        if(startAngle < 0){
            return turr.getAngle() > 0;
        }else{
            return turr.getAngle() < 0;

        }
    }
    
    @Override
    protected void end(){
        turr.setPower(0.0);
    }
    
}
