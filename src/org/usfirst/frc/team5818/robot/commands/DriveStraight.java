package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

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
	private double targetRatio;
	private double angleMult;


	public DriveStraight(double in, double pow, double minSpeedRatio) {
		inches = in;
		maxPow = pow;
		requires(Robot.runningrobot.driveTrain);
		setTimeout(in / 12);
		minSpeedRatio = minSpeedRatio;
		targetRatio = 1.0; //Ratio is LEFT/RIGHT
		angleMult = .01;
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
		leftVel = Math.abs(Robot.runningrobot.driveTrain.left.getSideVelocity());
		rightVel = Math.abs(Robot.runningrobot.driveTrain.right.getSideVelocity());
		
		double currRatio = leftVel/rightVel;
		
		targetRatio = Robot.runningrobot.track.getCurrentAngle()*angleMult;
		
		if(targetRatio < 0){
		    targetRatio = 1.0/Math.abs(targetRatio);
		}
		
		leftPowMult = 1.0;
		rightPowMult = currRatio/targetRatio;
		
		Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
		driveVec = driveVec.normalize(maxPow);
		
		Robot.runningrobot.driveTrain.setPowerLeftRight(driveVec);
		
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
