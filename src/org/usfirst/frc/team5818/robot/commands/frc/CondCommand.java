package org.usfirst.frc.team5818.robot.commands.frc;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

class CondCommand extends CommandGroup {

    private static final class CancelIfNot extends InstantCommand {

        private final BooleanSupplier condition;

        CancelIfNot(BooleanSupplier condition) {
            this.condition = condition;
        }

        @Override
        protected void initialize() {
            if (!condition.getAsBoolean()) {
                getGroup().cancel();
            }
        }
    }

    CondCommand(ConditionalCommand parent, BooleanSupplier condition, Command delegate) {
        addSequential(new CancelIfNot(condition));
        addSequential(delegate);
    }

}
