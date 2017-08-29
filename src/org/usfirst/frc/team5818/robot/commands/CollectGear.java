package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGear extends CommandGroup {

    private final double power;
    Collector collect;

    public CollectGear() {
        this(.7, 1000);
    }

    public CollectGear(double pow, double limitTimeout) {
        collect = Robot.runningRobot.collect;
        power = pow;
        this.addSequential(new LimitCollect(pow, limitTimeout));
        this.addSequential(new TimedCommand(.10));
        this.addSequential(new SetCollectorPower(false, 0.2, 0.2));
    }

    @Override
    protected void initialize() {
        collect.setBotPower(power);
        collect.setTopPower(power);
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
