package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BackAndForthAuto extends CommandGroup {
    
    private double distance;
    private double powerCap;
    
    public BackAndForthAuto(double dist, double pow) {
        distance = dist;
        powerCap = pow;
        this.addSequential(new DriveStraight(dist, pow, 1.8, DriveStraight.Camera.CAM_FORWARD, true));
        this.addSequential(new DriveStraight(dist, -pow, 1.8, DriveStraight.Camera.CAM_BACKWARD, true));
    }
    
}