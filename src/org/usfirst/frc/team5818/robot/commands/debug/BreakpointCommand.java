package org.usfirst.frc.team5818.robot.commands.debug;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class BreakpointCommand extends InstantCommand {

    @SuppressWarnings({ "unchecked", "unused" })
    private <T> T getGroupAs() {
        return (T) getGroup();
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected void end() {
        super.end();
    }

    protected void interrupted() {
        super.interrupted();
    };

}
