package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.autos.TwoGearAuto;
import org.usfirst.frc.team5818.robot.commands.AimTurret;
import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.ClimbControlCommand;
import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.ExposureHigh;
import org.usfirst.frc.team5818.robot.commands.ExposureLow;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.SetCollectorAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {

    public static double JOYSTICK_DEADBAND = .2;
    public static Vector2d DEADBAND_VEC = new Vector2d(JOYSTICK_DEADBAND, JOYSTICK_DEADBAND);

    public static final int BUT_QUICK_TURN = 2;

    public Joystick JS_FW_BACK;
    public Joystick JS_TURN;
    public Joystick JS_TURRET;
    public Joystick JS_COLLECTOR;

    public boolean turreting = true;
    public boolean was_turreting;

    public boolean controllingArm = true;
    public boolean wasControllingArm;

    public DriveMode dMode;
    public DriveCalculator driveCalc;

    public Driver() {
        JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
        JS_TURN = new Joystick(BotConstants.JS_TURN);
        JS_TURRET = new Joystick(BotConstants.JS_TURRET);
        JS_COLLECTOR = new Joystick(BotConstants.JS_COLLECTOR);

        JoystickButton twoGearButton = new JoystickButton(JS_FW_BACK, 1);
        twoGearButton.whenPressed(new TwoGearAuto());

        JoystickButton getGear = new JoystickButton(JS_FW_BACK, 2);
        getGear.whenPressed(new AutoSegment(Direction.BACKWARD, Side.LEFT));

        JoystickButton killPi = new JoystickButton(JS_TURN, 2);
        killPi.whenPressed(new ShutDownRPi());

        JoystickButton expLo = new JoystickButton(JS_TURN, 3);
        expLo.whenPressed(new ExposureLow());

        JoystickButton expHi = new JoystickButton(JS_TURN, 5);
        expHi.whenPressed(new ExposureHigh());

        JoystickButton gear = new JoystickButton(JS_TURN, 4);
        gear.whenPressed(new GearMode());

        JoystickButton tape = new JoystickButton(JS_TURN, 6);
        tape.whenPressed(new TapeMode());

        JoystickButton turret90 = new JoystickButton(JS_TURRET, 1);
        turret90.whenPressed(new SetTurretAngle(90.0));

        JoystickButton turretAim = new JoystickButton(JS_TURRET, 2);
        turretAim.whenPressed(new AimTurret());

        JoystickButton turretZero = new JoystickButton(JS_TURRET, 3);
        turretZero.whenPressed(new SetTurretAngle(0.0));

        JoystickButton climbMode = new JoystickButton(JS_TURRET, 4);
        climbMode.whenPressed(new ClimbControlCommand(JS_TURRET));
        
        JoystickButton collect = new JoystickButton(JS_COLLECTOR, 1);
        collect.whenPressed(new CollectGear());
        
        JoystickButton spit = new JoystickButton(JS_COLLECTOR, 2);
        spit.whileHeld(new SetCollectorPower());

        dMode = DriveMode.POWER;
        driveCalc = ArcadeDriveCalculator.INSTANCE;
    }

    public void teleopPeriodic() {
    }

}
