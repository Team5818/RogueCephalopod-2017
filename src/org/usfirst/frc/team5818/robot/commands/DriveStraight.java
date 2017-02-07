package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

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
		leftPowMult = 1;
		rightPowMult = 1;
		this.minSpeedRatio = minSpeedRatio;
	}
	
	public void execute() {
		Robot.runningrobot.driveTrain.left.setPower(leftPowMult);
		Robot.runningrobot.driveTrain.right.setPower(rightPowMult);
		leftVel = Robot.runningrobot.driveTrain.left.getSideVelocity();
		rightVel = Robot.runningrobot.driveTrain.right.getSideVelocity();
		if (leftVel >= rightVel) {
			if (leftVel / rightVel <= minSpeedRatio)
				rightPowMult = leftVel / rightVel;
			else
				rightPowMult = minSpeedRatio;
			leftPowMult = 1;
		} else if (rightVel < leftVel) {
			if (rightVel / leftVel <= minSpeedRatio)
				leftPowMult = rightVel / leftVel;
			else
				leftPowMult = 1;
			rightPowMult = 1;
		}
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut() || 
				Math.abs(Robot.runningrobot.driveTrain.left.getSidePosition()) - Math.abs(leftStart) >= Math.abs(inches) &&
				Math.abs(Robot.runningrobot.driveTrain.right.getSidePosition()) - Math.abs(rightStart) >= Math.abs(inches);
	}
	
}
