package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchFeed extends CommandGroup{

	private TapeMode tape;
	private GearMode gear;
	private CameraController cont;
	
	public SwitchFeed(){
	    tape = new TapeMode();
	    gear = new GearMode();
		cont = Robot.runningrobot.camCont;
		if(cont.isFront()){
		    this.addSequential(gear);
		}
		else{
		    this.addSequential(tape);
		}
	}
	

}