package org.usfirst.frc.team5818.robot.commands.collroll;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.DoNothing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectGear extends CommandGroup {

    public CollectGear(boolean waitForArm) {
        requires(Robot.runningrobot.collectorRollers);

        if (waitForArm) {
            // wait for arm to come down a little
            addSequential(new DoNothing(500, TimeUnit.MILLISECONDS));
        }
        // roll the rollers until current spike
        addSequential(new InitialCollectorRoll());
        // roll them slower after for a little bit
        addSequential(new LowPowerRoll());
    }

}
