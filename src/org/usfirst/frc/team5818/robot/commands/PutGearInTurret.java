package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PutGearInTurret extends CommandGroup {

    Collector collect = Robot.runningRobot.collect;

    public static final class Start extends Command {

        private PutGearInTurret delegate = new PutGearInTurret();
        private int loop;

        @Override
        protected void initialize() {
            loop = 0;
            Robot.runningRobot.turretSafetyChecks = false;
            Robot.runningRobot.turretZero.cancel();
        }

        @Override
        protected void execute() {
            if (loop > 0) {
                delegate.start();
            }
            loop++;
        }

        @Override
        protected boolean isFinished() {
            return loop > 1;
        }

    }

    public PutGearInTurret() {
        this.addSequential(new SetTurretAngle(0));
        CommandGroup moveToLoad = new CommandGroup();
        moveToLoad.addParallel(new SetArmAngle(Arm.LOAD_POSITION));
        // moveToLoad.addParallel(new SetCollectorPower(false, 0.6, 0.4));
        this.addSequential(moveToLoad);

        this.addSequential(new SetCollectorPower(true, 0.7, 1000));
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
