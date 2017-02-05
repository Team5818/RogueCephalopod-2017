package org.usfirst.frc.team5818.robot.subsystems;

public class CollectorActuator {
	public CollectorArmSide leftCollector;
	public CollectorArmSide rightCollector;
	private double angleOffset = 0;
	
	public CollectorActuator() {
		leftCollector = new CollectorArmSide(true);
		rightCollector = new CollectorArmSide(false);
	}
	
	public void setAngle(double angle) {
		rightCollector.setAngle(angle);
		leftCollector.setAngle(-1 * angle + angleOffset);
	}
	
	public void stop() {
		leftCollector.stop();
		rightCollector.stop();
	}
}
