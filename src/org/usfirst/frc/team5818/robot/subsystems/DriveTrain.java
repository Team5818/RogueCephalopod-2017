package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.subsystems.DriveTrainSide;
import org.usfirst.frc.team5818.robot.utils.BetterPIDController;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements PIDSource, PIDOutput{
    public DriveTrainSide left;
	public DriveTrainSide right;
	private	ADIS16448_IMU gyro;
	private Ultrasonic sanic;
//	private BetterPIDController spinController;
	
	private static double spinP = 0.0;
	private static double spinI = 0.0;
	private static double spinD = 0.0;
	
	public DriveTrain() {
		left = new DriveTrainSide(DriveTrainSide.Side.LEFT);
		right = new DriveTrainSide(DriveTrainSide.Side.RIGHT);
		sanic = new Ultrasonic(0,1);
//		gyro = new ADIS16448_IMU();
//		spinController = new BetterPIDController(spinP, spinI, spinD, this, this);
	}
	
	public Ultrasonic getSanic(){
	    return sanic;
	}
	
	public void enableSanic(){
	    sanic.setAutomaticMode(true);
	}
	
	public double readSanic(){
	    return sanic.getRangeInches();
	}
	
	public String askSanic(){
	    int idx = (int)Math.random()*4;
	    switch(idx){
	        case 0:
	            return "Gotta go fast!";
	        case 1:
	            return "Sanic's my name!";
	        case 2:
	            return "Speed's my game!";
	        case 3:
	            return "Stop hating on sanic!";
	        default:
	            return "Your too slooooowww!!";
	    }
	}
	
	public void setPowerLeftRight(double lpow, double rpow) {
//		spinController.disable();
        left.setPower(lpow);
        right.setPower(rpow);
	}
	
	public void setPowerLeftRight(Vector2d vec2){
		setPowerLeftRight(vec2.getX(), vec2.getY());
	}
	
	public void setVelocityLeftRight(double lvel, double rvel){
//		spinController.disable();
		left.driveVelocity(lvel);
		right.driveVelocity(rvel);
	}
	
	public void setVelocityLeftRight(Vector2d vec2){
		setVelocityLeftRight(vec2.getX(), vec2.getY());
	}
	
	public void driveDistance(double dist){
//		spinController.disable();
		left.driveDistance(dist);
		right.driveDistance(dist);
	}
	
	public void spinAngle(double ang){
//		spinController.disable();
//		spinController.setSetpoint(ang);
//		spinController.enable();
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
	
	public double getAverageDistance(){
		return (left.getSidePosition() + right.getSidePosition())/2;
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
	
	public double getHeading(){
		return gyro.getAngle();
	}
	
	public void stop() {
		left.setBrakeMode();
		right.setBrakeMode();
		this.setPowerLeftRight(0, 0);
	}

	@Override
	protected void initDefaultCommand() {}
	
	public BetterPIDController getSpinController(){
		return null;
	}
	
	public ADIS16448_IMU getGyro(){
		return gyro;
	}
	
	@Override
	public double pidGet(){
	    return 0;
//		return getHeading();
	}

	@Override
	public void pidWrite(double output) {
		left.setPower(output);
		right.setPower(-output);
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
}
