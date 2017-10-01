package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves the arm to the turret and unloads the gear into the turret.
 */
public class PutGearInTurret extends CommandGroup {

    Collector collect = Robot.runningRobot.collect;

    /**
     * Delays the parent command start by one command loop due to turret-zero checks.
     */
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
        this.addSequential(new SetTurretAngle(Turret.TURRET_CENTER_POS));
        CommandGroup moveToLoad = new CommandGroup();
        moveToLoad.addParallel(new SetArmAngle(Arm.LOAD_POSITION));
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
