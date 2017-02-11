package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Command {
	private double inches;
	private double maxPow;
	private double leftPowMult;
	private double rightPowMult;
	private double leftVel;
	private double rightVel;
	private double leftStart;
	private double rightStart;
	private double minSpeedRatio;


	public DriveStraight(double in, double pow, double minSpeedRatio) {
		inches = in;
		maxPow = pow;
		requires(Robot.runningrobot.driveTrain);
		setTimeout(in / 12);
		this.minSpeedRatio = minSpeedRatio;
	}
	
	@Override
	public void initialize() {
		leftPowMult = 1;
		rightPowMult = 1;
		Driver.joystickControlEnabled = false;
		leftStart = Robot.runningrobot.driveTrain.left.getSidePosition();
		rightStart = Robot.runningrobot.driveTrain.left.getSidePosition();
	}
	public void execute() {
		Robot.runningrobot.driveTrain.left.setPower(leftPowMult * maxPow);
		Robot.runningrobot.driveTrain.right.setPower(rightPowMult * maxPow);
		leftVel = Math.abs(Robot.runningrobot.driveTrain.left.getSideVelocity());
		rightVel = Math.abs(Robot.runningrobot.driveTrain.right.getSideVelocity());
		if (leftVel < rightVel) {
			if (leftVel / rightVel >= minSpeedRatio)
				rightPowMult = leftVel / rightVel;
			else
				rightPowMult = minSpeedRatio;
			leftPowMult = 1;
		} else {
			if (rightVel / leftVel >= minSpeedRatio)
				leftPowMult = rightVel / leftVel;
			else
				leftPowMult = minSpeedRatio;
			rightPowMult = 1;
		}
        SmartDashboard.putString("DB/String 0", String.format("%.3f", leftVel / rightVel));
        SmartDashboard.putString("DB/String 1", String.format("%.3f", leftPowMult));
        SmartDashboard.putString("DB/String 2", String.format("%.3f", rightPowMult));
	}
	@Override
	public void end() {
	    Robot.runningrobot.driveTrain.stop();
	    Driver.joystickControlEnabled = true;
	}@Override
	public synchronized void cancel() {
	    // TODO Auto-generated method stub
	    super.cancel();
	}
	@Override
	protected boolean isFinished() {
		return isTimedOut() || 
				Math.abs(Robot.runningrobot.driveTrain.left.getSidePosition()) - Math.abs(leftStart) >= Math.abs(inches) &&
				Math.abs(Robot.runningrobot.driveTrain.right.getSidePosition()) - Math.abs(rightStart) >= Math.abs(inches);
	}
	
}
