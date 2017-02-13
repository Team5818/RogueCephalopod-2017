package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.constants.BotConstants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionTracker extends Subsystem implements Runnable{

	private SerialPort rasPi;
	private Port port;
	private double currentAngle = -999;
	private String charBuffer = "";
	private Solenoid lightRing;
	
	public VisionTracker(){
	    lightRing = new Solenoid(1);
		port = Port.kMXP;
		rasPi = new SerialPort(9600, port);
		rasPi.setReadBufferSize(1);
		rasPi.setTimeout(.1);
	}
	
	public SerialPort getRasPi(){
		return rasPi;
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void read(){
		String output = "";
		try{
        	output += (char)(rasPi.read(1)[0] & 0xFF);
		}
		catch(Exception e){
			DriverStation.reportError("could not receive", false);
            return;
		}
        if(output.equals("\n")){
        	//DriverStation.reportError(charBuffer + "\n", false);
        	if(charBuffer.length() == 4){
        	    currentAngle = Integer.parseInt(charBuffer.substring(0, 4))
        	            *BotConstants.CAMERA_FOV/BotConstants.CAMERA_WIDTH/2.0;
        	}
    	    charBuffer = "";
        }
        else{
        	charBuffer += output;
        }
        // testing rpi serial
        // rasPi.writeString("test");
	}
	
	public void setLightsOn(boolean on) {
	    lightRing.set(on);
	}
	
	@Override
	public void run() {
		while(true){
			read();
		}
		
	}
	
	public double getCurrentAngle(){
		return currentAngle;
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub	
	}

}
