package org.usfirst.frc.team5818.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

public class ControlMotor extends Command {

    private final DoubleSupplier stick;
    private final PIDOutput out;

    public ControlMotor(DoubleSupplier stick, PIDOutput out) {
        this.stick = stick;
        this.out = out;
    }

    @Override
    protected void execute() {
        out.pidWrite(stick.getAsDouble());
    }

    @Override
    protected void end() {
        out.pidWrite(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
