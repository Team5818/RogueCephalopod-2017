package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Sets the arm limit to the climb position, so that it's easy to get there.
 */
public class SetClimbArmLimit extends InstantCommand {

    @Override
    protected void initialize() {
        Robot.runningRobot.arm.setLimitLow(Arm.CLIMB_POSITION);
    }

}
