package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.commands.AimTurret;
import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.ExposureHigh;
import org.usfirst.frc.team5818.robot.commands.ExposureLow;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.SetCollectorAngle;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearAuto;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {

    public Joystick JS_FW_BACK;
    public Joystick JS_TURN;
    public Joystick JS_TURRET;
    public Joystick JS_COLLECTOR;
    DriveTrain train;
    CameraController cont;
    public static double JOYSTICK_DEADBAND = .2;
    public static Vector2d DEADBAND_VEC = new Vector2d(JOYSTICK_DEADBAND, JOYSTICK_DEADBAND);

    public boolean turreting = true;
    public boolean was_turreting;

    public boolean controllingArm = true;
    public boolean wasControllingArm;

    public static final int BUT_QUICK_TURN = 2;

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

        JoystickButton setArm90 = new JoystickButton(JS_COLLECTOR, 1);
        setArm90.whenPressed(new SetCollectorAngle(90.0));

        JoystickButton setArm0 = new JoystickButton(JS_COLLECTOR, 2);
        setArm0.whenPressed(new SetCollectorAngle(0.0));

        train = Robot.runningrobot.driveTrain;
        dMode = DriveMode.POWER;
        driveCalc = ArcadeDriveCalculator.INSTANCE;
        cont = Robot.runningrobot.camCont;
    }

    public void teleopPeriodic() {
        handleDeadbands();
        controlTurret();
        controlCollector();
    }
		driveVector = MathUtil.adjustDeadband(driveVector, DEADBAND_VEC);

    public void controlTurret() {
        if (turreting) {
			Robot.runningrobot.turret.setPower(MathUtil.adjustDeadband(JS_TURRET, DEADBAND_VEC).getX());
        } else if (!turreting && was_turreting) {
            Robot.runningrobot.turret.setPower(0.0);
        }

    }

    public void handleDeadbands() {
        was_turreting = turreting;
        turreting = MathUtil.deadband(JS_TURRET, JOYSTICK_DEADBAND);
        wasControllingArm = controllingArm;
        controllingArm = MathUtil.deadband(JS_COLLECTOR, JOYSTICK_DEADBAND);
    }

    public void controlCollector() {
        if (controllingArm) {
	        Robot.runningrobot.collector.setPower(-1 * MathUtil.adjustDeadband(JS_COLLECTOR, DEADBAND_VEC).getY());
        } else if (!controllingArm && wasControllingArm) {
            Robot.runningrobot.collector.setPower(0.0);
        }
    }
}
