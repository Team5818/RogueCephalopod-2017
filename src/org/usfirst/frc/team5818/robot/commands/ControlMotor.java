package org.usfirst.frc.team5818.robot.commands;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.MathUtil;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ControlMotor extends Command {

    private static final class CMSystem extends Subsystem {
        private static final CMSystem INSTANCE = new CMSystem();

        @Override
        protected void initDefaultCommand() {
        }
    }

    private final DoubleSupplier stick;
    private final PIDOutput out;

    public ControlMotor(DoubleSupplier stick, PIDOutput out) {
        this.stick = stick;
        this.out = out;
        requires(CMSystem.INSTANCE);
    }

    @Override
    protected void execute() {
        out.pidWrite(MathUtil.adjustDeadband(new Vector2d(stick.getAsDouble(), 0), Driver.DEADBAND_VEC).getX());
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
