package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraController extends Subsystem{

    private enum Camera{
        FRONT, BACK
    }
    
    public static double CAMERA_EXPOSURE_HIGH = 156;
    public static double CAMERA_EXPOSURE_LOW = 5;
    
    private Camera currCam;
    private SerialPort rPi;
    private double frontExposure;
    
    public CameraController(){
        rPi = Robot.runningrobot.track.getRasPi();
        currCam = Camera.FRONT;
        frontExposure = CAMERA_EXPOSURE_LOW;
    }
    
    public void switchFeed(){
        try{
            rPi.writeString("switch\n"); 
            DriverStation.reportError("Wrote", false);
            if(currCam.equals(Camera.BACK)){
                currCam = Camera.FRONT;
            }else{
                currCam = Camera.BACK;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void gearMode(){
        if(currCam.equals(Camera.FRONT)){
            switchFeed();
        }
    }
    
    public void tapeMode(){
        if(currCam.equals(Camera.BACK)){
            switchFeed();
        }
    }
    
    public void switchExposure(){
        if(frontExposure == CAMERA_EXPOSURE_LOW){
            exposureHigh();
        }
        else{
            exposureLow();
        }
    }
    
    public void exposureHigh(){
        try{
            rPi.writeString("exphi");
            frontExposure = CAMERA_EXPOSURE_HIGH;
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void exposureLow(){
        try{
            rPi.writeString("explo");
            frontExposure = CAMERA_EXPOSURE_LOW;
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void shutDown(){
        try{
            rPi.writeString("shutdown");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Camera getCurrentCam(){
        return currCam;
    }
    
    
    public boolean isFront(){
        return currCam.equals(Camera.FRONT);
    }
    
    public double getFrontExposure(){
        return frontExposure;
    }
    
    @Override
    protected void initDefaultCommand() {}
    
    
    
    
}
