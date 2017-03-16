package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGear extends CommandGroup {

    Collector collect;

    public CollectGear() {
        this(1000);
    }

    public CollectGear(double limitTimeout) {
        collect = Robot.runningRobot.collect;
        this.addSequential(new LimitCollect(.7, limitTimeout));
        //this.addSequential(new CollectGearCurrent(.7, currentTimeout));
        this.addSequential(new TimedCommand(.1));
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

    @Override
    protected void interrupted() {
        end();
    }

}
