package org.usfirst.frc.team5818.robot.commands.debug;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A breakpoint command is used to debug command-based routines.
 * 
 * <p>
 * It logs to the dashboard the last-hit breakpoint, and allows you to stop inside of it to examine state.
 * </p>
 */
public class BreakpointCommand extends Command {

    private final String name;

    public BreakpointCommand(String name) {
        setTimeout(1);
        this.name = name;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private <T> T getGroupAs() {
        return (T) getGroup();
    }

    @Override
    protected void initialize() {
        SmartDashboard.putString("Message", name);
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

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
