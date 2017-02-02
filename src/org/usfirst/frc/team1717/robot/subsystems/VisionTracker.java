package org.usfirst.frc.team1717.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionTracker extends Subsystem implements Runnable{

	private SerialPort rasPi;
	private Port port;
	private int currentX = -999;
	private int currentY = -999;
	private int currentR = -999;
	private String charBuffer = "";
	
	public VisionTracker(){
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
        	if(charBuffer.length() == 12){
        	    currentX = Integer.parseInt(charBuffer.substring(0, 4));
        	    currentY = Integer.parseInt(charBuffer.substring(4, 8));
        	    currentR = Integer.parseInt(charBuffer.substring(8, 12));
        	}
    	    charBuffer = "";
        }
        else{
        	charBuffer += output;
        }
	}
	
	@Override
	public void run() {
		while(true){
			read();
		}
		
	}
	
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	public int getCurrentR(){
		return currentR;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub	
	}

}
