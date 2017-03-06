package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class PlaceWithLimit extends Command {

    private Turret turr;

    public PlaceWithLimit() {
        turr = Robot.runningRobot.turret;
    }

    @Override
    protected void initialize() {
        double power = .40;
        for(int i = 0; i < 2; i++){
            turr.extend(true);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            if (!turr.getLimit()) {
                turr.extend(false);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                turr.setPower(power);
                try {
                    Thread.sleep(50*(i+1));
                } catch (InterruptedException e) {
                }
                turr.setPower(0.0);
                power *= -1;
            }
        }
        turr.extend(true);

    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return turr.getLimit();
    }

    protected void end() {
        turr.extend(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
        turr.punch(true);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
        }
        turr.extend(false);
        turr.punch(false);
    }

}
