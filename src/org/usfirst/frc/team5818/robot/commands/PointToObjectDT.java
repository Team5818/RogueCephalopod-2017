package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.SpinRobot;
import edu.wpi.first.wpilibj.command.Command;

public class PointToObjectDT extends Command {
    public enum PointTo {
        GEARS,
        TAPE
    }
    
    public PointTo pt;
    
    public PointToObjectDT(PointTo p, double tmot) {
        pt = p;
        requires(Robot.runningrobot.driveTrain);
        setTimeout(tmot);
    }
    
    @Override
    public void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}
