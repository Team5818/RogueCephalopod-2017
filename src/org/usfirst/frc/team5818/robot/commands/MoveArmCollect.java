package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveArmCollect extends CommandGroup {

    private CommandGroup moveArm;

    public MoveArmCollect() {
        moveArm = new CommandGroup();
        moveArm.addParallel(new CollectGear(1, 1000));
        moveArm.addParallel(new SetArmAngle(Arm.COLLECT_POSITION));
        this.addSequential(moveArm);
        this.addSequential(new SetCollectorPower(false, 0.2, 0.2));
        this.addSequential(new SetArmAngle(Arm.MID_POSITION));
    }

    @Override
    protected void end() {
        Robot.runningRobot.collect.setBotPower(0.0);
        Robot.runningRobot.collect.setTopPower(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
