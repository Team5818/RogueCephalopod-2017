package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PutGearInTurret extends CommandGroup {

    Collector collect = Robot.runningRobot.collect;

    public PutGearInTurret() {
        this.addSequential(new SetTurretAngle(0));
        this.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
        this.addSequential(new SetCollectorPower(true, 0.7, 1));
    }
    
    @Override
    protected void initialize() {
        Robot.runningRobot.turretSafetyChecks = false;
    }

    @Override
    protected void end() {
        Robot.runningRobot.turretSafetyChecks = false;
        collect.setBotPower(0);
        collect.setTopPower(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
