package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command{

    private Climber climb;
    private static final double DEFAULT_TIMEOUT = 3;
    private static final double CURRENT_THRESH = 30;

    
    public Climb(double timeout){
        climb = Robot.runningRobot.climb;
        setTimeout(timeout);
    }
    
    public Climb(){
        this(DEFAULT_TIMEOUT);
    }
    
    @Override
    protected void initialize(){
        climb.setPower(.7);
    }
    
    @Override
    protected void execute(){
        climb.setPower(.7);
    }
    
    @Override 
    protected void end(){
        climb.setPower(0.0);
    }
    
    @Override
    protected boolean isFinished() {
       return climb.getLeftCurrent() > CURRENT_THRESH || climb.getRightCurrent() > CURRENT_THRESH || isTimedOut();
    }

}
