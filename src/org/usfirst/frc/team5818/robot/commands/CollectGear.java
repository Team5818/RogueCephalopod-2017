package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGear extends CommandGroup {

    Collector collect;

    public CollectGear() {
        this(.7, 10);
    }

    public CollectGear(double initialTimeout, double currentTimeout) {
        collect = Robot.runningRobot.collect;
        this.addSequential(new TimedCommand(initialTimeout));
        this.addSequential(new CollectGearCurrent(.7, currentTimeout));
        this.addSequential(new TimedCommand(.3));
    }

    @Override
    protected void initialize() {
        collect.setBotPower(.7);
        collect.setTopPower(.7);
    }

    @Override
    protected void end() {
        collect.setBotPower(0.0);
        collect.setTopPower(0.0);
    }

}
