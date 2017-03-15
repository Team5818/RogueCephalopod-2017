package org.usfirst.frc.team5818.robot.commands.frc;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

public abstract class ConditionalCommand extends CommandGroup {

    protected ConditionalCommand(Command onTrue) {
        this(onTrue, new InstantCommand());
    }

    protected ConditionalCommand(Command onTrue, Command onFalse) {
        // race the two conditions at the same time -- only one will truly fire
        addParallel(new CondCommand(this, this::condition, onTrue));
        addParallel(new CondCommand(this, this::notCondition, onFalse));
    }

    protected abstract boolean condition();

    private final boolean notCondition() {
        return !condition();
    }

}
