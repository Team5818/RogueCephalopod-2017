package org.usfirst.frc.team5818.robot.subsystems;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.commands.ClimbControlCommand;
import org.usfirst.frc.team5818.robot.commands.DriveControlCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem{
    
    private CANTalon left1;
    private CANTalon left2;
    private CANTalon right1;
    private CANTalon right2;
    
    public Climber(){
        left1 = new CANTalon(RobotMap.LEFT_CLIMB_TALON_1);
        //left2 = new CANTalon(RobotMap.LEFT_CLIMB_TALON_2);
        right1 = new CANTalon(RobotMap.RIGHT_CLIMB_TALON_1);
        //right2 = new CANTalon(RobotMap.RIGHT_CLIMB_TALON_2);
    }
    
    public void setPower(double pow){
	if(pow < 0.0){
	    left1.set(pow);
	    //left2.set(pow);
	    right1.set(pow);
	    //right2.set(pow);
	}
	else{
	    left1.set(0.0);
	    //left2.set(pow);
	    right1.set(0.0);
	    //right2.set(pow);
	}
    }
    
    public double getLeftCurrent(){
        return left1.getOutputCurrent();// + left2.getOutputCurrent())/2;
    }
    
    public double getRightCurrent(){
        return right1.getOutputCurrent();// + right2.getOutputCurrent())/2;
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimbControlCommand());
    }

}
