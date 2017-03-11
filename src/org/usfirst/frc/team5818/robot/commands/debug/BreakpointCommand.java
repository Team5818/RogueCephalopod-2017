package org.usfirst.frc.team5818.robot.commands.debug;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
