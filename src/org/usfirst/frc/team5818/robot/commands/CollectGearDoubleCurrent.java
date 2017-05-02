package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CollectGearDoubleCurrent extends Command {

    private static final double DEFAULT_CURRENT_THRESH = 5.5;
    private static final double CURRENT_SPIKE_TIME = 0.3; // in seconds
    
    private double currThresh;
    private double power;
    private final Collector collect = Robot.runningRobot.collect;
    private double currentTimeout;
    private boolean finished;

    public CollectGearDoubleCurrent(double thresh, double pow, double timeout) {
        setTimeout(timeout);
        currThresh = thresh;
        power = pow;
    }

    public CollectGearDoubleCurrent(double pow, double timeout) {
        this(DEFAULT_CURRENT_THRESH, pow, timeout);
    }

    @Override
    protected void initialize() {
        currentTimeout = 0;
        finished = false;
        collect.setTopPower(power);
        collect.setBotPower(power);
    }

    @Override
    protected void execute() {
        collect.setTopPower(power);
        collect.setBotPower(power);
        if (collect.getBotCurrent() > currThresh) {
            if (Double.compare(currentTimeout, 0) == 0) {
                // 0 timeout -- set it
                currentTimeout = Timer.getFPGATimestamp() + CURRENT_SPIKE_TIME;
            } else {
                if (currentTimeout > Timer.getFPGATimestamp()) {
                    finished = true;
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return finished || isTimedOut();
    }

}
