
package org.usfirst.frc.team1717.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

/**
 *
 */
public class DrivePowerDistance extends Command {

    public DrivePowerDistance(double power, double inches) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.runningrobot.driveTrain);
        this.power = power;
        this.inches = inches;
    }

    // Called just before this Command runs the first time
    private DriveTrain dt = Robot.runningrobot.driveTrain;
    private double power;
    private double inches;
    
    protected void initialize() {
    	dt.resetEncs();
    	dt.setCoastMode();
    	setTimeout(1.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dt.setPowerLeftRight(power, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(dt.getAvgSidePosition()) > inches) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	dt.setPowerLeftRight(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	dt.stop();
    }
    

}
