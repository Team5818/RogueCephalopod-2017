
package org.usfirst.frc.team1717.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;

/**
 *
 */
public class DrivePowerDistance extends Command {

	public DrivePowerDistance(double power, double inches, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.runningrobot.driveTrain);
        this.power = power;
        this.inches = inches;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    private DriveTrain dt = Robot.runningrobot.driveTrain;
    private double power;
    private double inches;
    private double timeout;
    private double startPos;
    
    protected void initialize() {
    	// dt.setCoastMode();
    	setTimeout(timeout);
    	startPos = Math.abs(dt.getAvgSidePosition());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dt.setPowerLeftRight(power, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(Math.abs(dt.getAvgSidePosition()) - startPos) > inches) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	dt.stop();
//    	dt.setPowerLeftRight(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	dt.stop();
    }
    

}
