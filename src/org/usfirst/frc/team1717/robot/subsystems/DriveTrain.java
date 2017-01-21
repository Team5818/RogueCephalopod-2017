package org.usfirst.frc.team1717.robot.subsystems;

import utils.Vector2d;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrainSide;

public class DriveTrain {
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
	
	public void brake(){
		left.setPower(0.0);
		right.setPower(0.0);
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
	
}
