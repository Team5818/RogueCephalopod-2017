package org.usfirst.frc.team5818.robot.controllers;

import org.usfirst.frc.team5818.robot.autos.TwoGearAuto;
import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.ClimbControlCommand;
import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.MovePiston;
import org.usfirst.frc.team5818.robot.commands.ExposureHigh;
import org.usfirst.frc.team5818.robot.commands.ExposureLow;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.SetCollectorAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.ShutDownRPi;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
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
    public Joystick JS_TURRET;
    public Joystick JS_COLLECTOR;

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
        getGear.whenPressed(new AutoSegment(Direction.BACKWARD, Side.LEFT, null));

        JoystickButton shiftLow = new JoystickButton(JS_FW_BACK, 3);
        shiftLow.whenPressed(new ShiftGears(BotConstants.LOW_GEAR_VALUE));

        JoystickButton shiftHigh = new JoystickButton(JS_FW_BACK, 4);
        shiftHigh.whenPressed(new ShiftGears(BotConstants.HIGH_GEAR_VALUE));

        JoystickButton expLo = new JoystickButton(JS_TURN, 3);
        expLo.whenPressed(new ExposureLow());

        JoystickButton expHi = new JoystickButton(JS_TURN, 5);
        expHi.whenPressed(new ExposureHigh());

        JoystickButton gear = new JoystickButton(JS_TURN, 4);
        gear.whenPressed(new GearMode());

        JoystickButton tape = new JoystickButton(JS_TURN, 6);
        tape.whenPressed(new TapeMode());

        JoystickButton placeGear = new JoystickButton(JS_TURRET, 1);
        placeGear.whenPressed(new MovePiston(MovePiston.Position.PLACE));
        placeGear.whenReleased(new MovePiston(MovePiston.Position.RETRACT));

        JoystickButton turretMinus90 = new JoystickButton(JS_TURRET, 4);
        turretMinus90.whenPressed(new SetTurretAngle(-90.0));

        JoystickButton turretAim = new JoystickButton(JS_TURRET, 3);
        turretAim.whenPressed(new SetTurretAngle(-0.0));

        JoystickButton turretZero = new JoystickButton(JS_TURRET, 5);
        turretZero.whenPressed(new SetTurretAngle(90.0));

        JoystickButton climbMode = new JoystickButton(JS_TURRET, 2);
        climbMode.whenPressed(new ClimbControlCommand(JS_TURRET));

        JoystickButton collect = new JoystickButton(JS_COLLECTOR, 1);
        collect.whenPressed(new CollectGear());

        JoystickButton spit = new JoystickButton(JS_COLLECTOR, 2);
        spit.whileHeld(new SetCollectorPower(true));

        JoystickButton armCollect = new JoystickButton(JS_COLLECTOR, 4);
        armCollect.whenPressed(new SetCollectorAngle(Collector.COLLECT_POSITION));

        JoystickButton armMid = new JoystickButton(JS_COLLECTOR, 3);
        armMid.whenPressed(new SetCollectorAngle(Collector.MID_POSITION));

        JoystickButton armLoad = new JoystickButton(JS_COLLECTOR, 5);
        armLoad.whenPressed(new SetCollectorAngle(Collector.LOAD_POSITION));

        JoystickButton eject = new JoystickButton(JS_COLLECTOR, 6);
        eject.whenPressed(new SetCollectorPower(false));

        dMode = DriveMode.POWER;
        driveCalc = RadiusDriveCalculator.INSTANCE;
    }

    public void teleopPeriodic() {
    }

}
