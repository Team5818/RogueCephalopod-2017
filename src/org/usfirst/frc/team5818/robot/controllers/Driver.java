package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.autos.TwoGearAuto;
import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.ChangeMini;
import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.SetPunchTurret;
import org.usfirst.frc.team5818.robot.commands.ExposureHigh;
import org.usfirst.frc.team5818.robot.commands.ExposureLow;
import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.PlaceGear;
import org.usfirst.frc.team5818.robot.commands.QuickPlace;
import org.usfirst.frc.team5818.robot.commands.SetCollectorAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.StopVisionDriving;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.VisionStop;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {

    public static double JOYSTICK_DEADBAND = .05;
    public static Vector2d DEADBAND_VEC = new Vector2d(JOYSTICK_DEADBAND, JOYSTICK_DEADBAND);

    public static final double TWIST_DEADBAND = .4;

    public Joystick JS_FW_BACK;
    public Joystick JS_TURN;
    public Joystick BUTTON_PAD;
    public Joystick JS_TURRET;
    public Joystick JS_COLLECTOR;

    public DriveMode dMode;
    public DriveCalculator driveCalc;

    public Driver() {
        JS_FW_BACK = new Joystick(BotConstants.JS_FW_BACK);
        JS_TURN = new Joystick(BotConstants.JS_TURN);
        BUTTON_PAD = new Joystick(BotConstants.JS_BUTTONS);
        JS_TURRET = new Joystick(BotConstants.JS_TURRET);
        JS_COLLECTOR = new Joystick(BotConstants.JS_COLLECTOR);

        // JoystickButton twoGearButton = new JoystickButton(JS_FW_BACK, 1);
        // twoGearButton.whenPressed(new TwoGearAuto());
        //
        // JoystickButton driveStraight = new JoystickButton(JS_FW_BACK, 2);
        // driveStraight.whenPressed( DriveAtRatio.withDeadReckon(b -> {
        // b.inches(40);
        // b.maxPower(-0.4);
        // b.targetRatio(1);
        // b.stoppingAtEnd(true);
        // }));

        JoystickButton shiftLow = new JoystickButton(JS_FW_BACK, 1);
        shiftLow.whenPressed(new ShiftGears(BotConstants.LOW_GEAR_VALUE));

        JoystickButton shiftHigh = new JoystickButton(JS_FW_BACK, 2);
        shiftHigh.whenPressed(new ShiftGears(BotConstants.HIGH_GEAR_VALUE));

        JoystickButton placeGear = new JoystickButton(JS_TURN, 1);
        placeGear.whenPressed(new PlaceGear());

        JoystickButton quickPlace = new JoystickButton(JS_TURN, 2);
        quickPlace.whenPressed(new QuickPlace());

        JoystickButton turret90 = new JoystickButton(BUTTON_PAD, 1);
        turret90.whenPressed(new SetTurretAngle(90.0));
        turret90.whileHeld(new VisionStop());
        turret90.whenReleased(new StopVisionDriving());

        JoystickButton turretZero = new JoystickButton(BUTTON_PAD, 2);
        turretZero.whenPressed(new SetTurretAngle(0.0));

        JoystickButton turretMinus90 = new JoystickButton(BUTTON_PAD, 3);
        turretMinus90.whenPressed(new SetTurretAngle(-90.0));
        turretMinus90.whileHeld(new VisionStop());
        turretMinus90.whenReleased(new StopVisionDriving());

        JoystickButton collect = new JoystickButton(JS_COLLECTOR, 1);
        collect.whenPressed(new CollectGear());

        JoystickButton spit = new JoystickButton(JS_COLLECTOR, 2);
        spit.whileHeld(new SetCollectorPower(true));

        JoystickButton armCollect = new JoystickButton(BUTTON_PAD, 6);
        armCollect.whenPressed(new SetCollectorAngle(Collector.COLLECT_POSITION));

        JoystickButton armMid = new JoystickButton(BUTTON_PAD, 5);
        armMid.whenPressed(new SetCollectorAngle(Collector.MID_POSITION));

        JoystickButton armLoad = new JoystickButton(BUTTON_PAD, 4);
        armLoad.whenPressed(new SetCollectorAngle(Collector.LOAD_POSITION));

        JoystickButton gearMode = new JoystickButton(BUTTON_PAD, 11);
        gearMode.whenPressed(new GearMode());
        gearMode.whenReleased(new TapeMode());
        
        JoystickButton rawExtend = new JoystickButton(BUTTON_PAD, 10);
        rawExtend.whenPressed(new SetExtendTurret(true));
        rawExtend.whenReleased(new SetExtendTurret(false));
        
        JoystickButton rawPunch = new JoystickButton(BUTTON_PAD, 9);
        rawPunch.whenPressed(new SetPunchTurret(true));
        rawPunch.whenReleased(new SetPunchTurret(false));

        JoystickButton rightMini = new JoystickButton(BUTTON_PAD, 12);
        rightMini.whenPressed(new ChangeMini(Side.RIGHT));
        rightMini.whenReleased(new ChangeMini(Side.LEFT));

        dMode = DriveMode.POWER;
        driveCalc = RadiusDriveCalculator.INSTANCE;
    }

    public void teleopPeriodic() {
    }

}
