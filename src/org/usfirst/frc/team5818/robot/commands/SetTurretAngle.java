package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

public class SetTurretAngle extends Command{
	
	private Turret turr;
	private double targetAng;
	public static final double DEGREES_TOLERANCE = 2;
	
	public SetTurretAngle(double ang){
		turr = Robot.runningrobot.turret;
		targetAng = ang;
	}
	
	public void initialize(){
		turr.getAngController().setAbsoluteTolerance(DEGREES_TOLERANCE);
		turr.setAng(targetAng);
	}
	
	@Override
	protected void execute(){
	}
	
	@Override
	protected boolean isFinished() {
		return turr.getAngController().onTarget();
	}
	
	@Override
	protected void end(){
		turr.getAngController().disable();
	}
	
	@Override
	protected void interrupted(){
		end();
	}

}
