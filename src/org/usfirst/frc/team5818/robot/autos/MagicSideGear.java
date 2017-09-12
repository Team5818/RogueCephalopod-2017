package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.MagicSpin;
import org.usfirst.frc.team5818.robot.commands.MagicSpinToVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MagicSideGear extends CommandGroup{
    
    private Position position;
    private double firstDistance;
    private double turnAngle;
    private double secondDistance;
    
    private enum Position{
        BLUE_BOILER, BLUE_OPPOSITE, RED_BOILER, RED_OPPOSITE
    }
    
    public MagicSideGear(Position pos) {
        position = pos;
        switch(position) {
            case BLUE_BOILER:
                firstDistance = 0.0;
                turnAngle = 0.0;
                secondDistance = 0.0;
                break;
            case BLUE_OPPOSITE:
                firstDistance = 0.0;
                turnAngle = 0.0;
                secondDistance = 0.0;
                break;
            case RED_BOILER:
                firstDistance = 0.0;
                turnAngle = 0.0;
                secondDistance = 0.0;
                break;
            case RED_OPPOSITE:
                firstDistance = 0.0;
                turnAngle = 0.0;
                secondDistance = 0.0;
                break;
        }
        
        this.addSequential(new MagicDrive(firstDistance));
        this.addSequential(new MagicSpin(turnAngle));
        this.addSequential(new MagicSpinToVision(secondDistance));
        this.addSequential(new MagicDrive(-firstDistance));
        this.addSequential(new MagicSpin(Math.PI));
        this.addSequential(new MagicDrive(320.0));
    }
    
}
