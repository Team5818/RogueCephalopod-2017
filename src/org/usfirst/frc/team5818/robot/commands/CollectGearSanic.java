package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;

import edu.wpi.first.wpilibj.command.Command;

public class CollectGearSanic extends Command {

    private static final double DEFAULT_SONIC_THRESH = 20;
    private double sonicThresh;
    private double power;
    private final CollectorRollers collectorRollers = Robot.runningRobot.roll;

    public CollectGearSanic(double thresh, double pow) {
        sonicThresh = thresh;
        power = pow;
    }
    
    public CollectGearSanic(double pow) {
        this(DEFAULT_SONIC_THRESH,pow);
    }

    @Override
    protected void initialize(){
        collectorRollers.setTopPower(power);
        collectorRollers.setBotPower(power);
    }
    
    @Override
    protected void execute() {
        collectorRollers.setTopPower(power);
        collectorRollers.setBotPower(power);
    }

    @Override
    protected boolean isFinished() {
        return collectorRollers.readSanic() < sonicThresh;
    }
    
    @Override
    protected void end(){}
}