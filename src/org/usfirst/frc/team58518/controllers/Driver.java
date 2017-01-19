package org.usfirst.frc.team58518.controllers;

import org.usfirst.frc.team5818.robot.constants.BotConstants;

import edu.wpi.first.wpilibj.Joystick;

public class Driver {
	public Joystick JS_FW_BACK;
	public Joystick JS_TURN;

	private double FW_BACK_X;
	private double FW_BACK_Y;
	private double TURN_X;
	private double TURN_Y;
	
	public Driver() {
		JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
		JS_TURN = new Joystick(BotConstants.JS_TURN);
	}
	
}
