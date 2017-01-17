package org.usfirst.frc.team5818.robot.subsystems;

public class DriveTrain {
	public DriveTrainSide left;
	public DriveTrainSide right;
	
	public DriveTrain() {
		left = new DriveTrainSide(DriveTrainSide.Side.LEFT);
		right = new DriveTrainSide(DriveTrainSide.Side.RIGHT);
	}
}
