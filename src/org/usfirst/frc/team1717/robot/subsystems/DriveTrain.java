package org.usfirst.frc.team1717.robot.subsystems;

import utils.Vector2d;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrainSide;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	public DriveTrainSide left;
	public DriveTrainSide right;
		
	public DriveTrain() {
		left = new DriveTrainSide(DriveTrainSide.Side.LEFT);
		right = new DriveTrainSide(DriveTrainSide.Side.RIGHT);
	}
	
	public void setPowerLeftRight(double lpow, double rpow) {
        left.setPower(lpow);
        right.setPower(rpow);
	}
	
	public void setPowerLeftRight(Vector2d vec2){
		setPowerLeftRight(vec2.getX(), vec2.getY());
	}
	
	public void setVelocityLeftRight(double lvel, double rvel){
		left.driveVelocity(lvel);
		right.driveVelocity(rvel);
	}
	
	public void setVelocityLeftRight(Vector2d vec2){
		setVelocityLeftRight(vec2.getX(), vec2.getY());
	}
	
	public void driveDistance(double dist){
		left.driveDistance(dist);
		right.driveDistance(dist);
	}
	
	public void setPIDSourceType(PIDSourceType type) {
		left.setPIDSourceType(type);
		right.setPIDSourceType(type);
	}
	
	public DriveTrainSide getLeftSide(){
		return left;
	}
	
	public DriveTrainSide getRightSide(){
		return right;
	}
	
	public void resetEncs() {
		left.resetEnc();
		right.resetEnc();
	}
	
	public void setCoastMode() {
		left.setCoastMode();
		right.setCoastMode();
	}
	
	public void setBrakeMode() {
		left.setBrakeMode();
		right.setBrakeMode();
	}

	public void setPowerStraight(double numIn) {
		left.setPower(numIn);
		right.setPower(numIn);
	}
	
	public double getAvgSidePosition() {
		return (left.getSidePosition() + right.getSidePosition()) / 2;
	}
	
	public void stop() {
		left.setBrakeMode();
		right.setBrakeMode();
		this.setPowerLeftRight(0, 0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}
