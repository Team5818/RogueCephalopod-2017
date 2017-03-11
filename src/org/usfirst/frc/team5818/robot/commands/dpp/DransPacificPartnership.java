package org.usfirst.frc.team5818.robot.commands.dpp;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class DransPacificPartnership extends CommandGroup {

    public DransPacificPartnership() {
        this(0);
        setTimeout(1);
    }

    DransPacificPartnership(int loopCount) {
        if (loopCount >= 2) {
            return;
        }
        // Extend Turret
        addSequential(new SetExtendTurret(true));
        addSequential(new TimedCommand(0.35));

        // Check Limit
        CommandGroup onFail = new CommandGroup();
        onFail.addSequential(new SetExtendTurret(false));
        onFail.addSequential(new DPPTurnTurret(loopCount));
        onFail.addSequential(new DransPacificPartnership(loopCount + 1));
        addSequential(new ConditionalCommand(onFail) {

            @Override
            protected boolean condition() {
                return !Robot.runningRobot.turret.getLimit();
            }
        });
    }

    @Override
    protected boolean isFinished() {
        return super.isFinished() || isTimedOut() || Robot.runningRobot.turret.getLimit();
    }
    
}
