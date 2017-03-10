package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SpitGear extends CommandGroup {

    public SpitGear() {
        addSequential(new SetArmAngle(Arm.MID_POSITION));
        addSequential(new SetCollectorPower(false));
    }

}
