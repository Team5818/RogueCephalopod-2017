package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveArmCollect extends CommandGroup {

    private CommandGroup moveArm;

    public MoveArmCollect() {
        moveArm = new CommandGroup();
        moveArm.addParallel(new CollectGear());
        moveArm.addParallel(new SetArmAngle(Arm.COLLECT_POSITION));
        this.addSequential(moveArm);
        this.addSequential(new SetArmAngle(Arm.MID_POSITION));
    }
}
