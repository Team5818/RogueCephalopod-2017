package org.usfirst.frc.team5818.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSourceType;

public class DriveTrain {
	public DriveTrainSide left;
	public DriveTrainSide right;
	
	private PIDSourceType pidType = left.pidType;
	
	public DriveTrain() {
		left = new DriveTrainSide(DriveTrainSide.Side.LEFT);
		right = new DriveTrainSide(DriveTrainSide.Side.RIGHT);
	}
	
	public void setPIDSourceType(PIDSourceType type) {
		left.setPIDSourceType(type);
		right.setPIDSourceType(type);
		pidType = type;
	}
	
	public void drive() {
		if (pidType == PIDSourceType.kDisplacement) {
			
		}
		else {
			 
		}
	}
}
