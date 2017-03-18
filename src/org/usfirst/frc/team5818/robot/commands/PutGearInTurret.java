package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PutGearInTurret extends CommandGroup {

    Collector collect = Robot.runningRobot.collect;

    public PutGearInTurret() {
        this.addSequential(new SetTurretAngle(0));
        CommandGroup moveToLoad = new CommandGroup();
        moveToLoad.addParallel(new SetArmAngle(Arm.LOAD_POSITION));
        moveToLoad.addParallel(new SetCollectorPower(false, 0.6, 0.4));
        this.addSequential(moveToLoad);

        this.addSequential(new SetCollectorPower(true, 0.7, 1000));
    }

    @Override
    protected void initialize() {
        Robot.runningRobot.turretSafetyChecks = false;
        Robot.runningRobot.turretZero.cancel();
    }

    @Override
    protected void end() {
        Robot.runningRobot.turretSafetyChecks = true;
        collect.setBotPower(0);
        collect.setTopPower(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
