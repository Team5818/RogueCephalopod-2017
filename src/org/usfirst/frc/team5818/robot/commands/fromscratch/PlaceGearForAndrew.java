package org.usfirst.frc.team5818.robot.commands.fromscratch;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class PlaceGearForAndrew extends TimedCommand {

    private final Turret turret = Robot.runningRobot.turret;

    public PlaceGearForAndrew(double timeout) {
        super(timeout);
    }

    @Override
    protected void execute() {
        turret.extend(true);
        turret.punch(true);
    }

    @Override
    protected void end() {
        turret.punch(false);
        turret.extend(false);
    }

}
