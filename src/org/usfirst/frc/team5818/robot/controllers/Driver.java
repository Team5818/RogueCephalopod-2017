package org.usfirst.frc.team5818.robot.controllers;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team5818.robot.RobotMap;
import org.usfirst.frc.team5818.robot.autos.TwoGearAuto;
import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.ControlMotor;
import org.usfirst.frc.team5818.robot.commands.ExposureHigh;
import org.usfirst.frc.team5818.robot.commands.ExposureLow;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.SetCollectorAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;
import org.usfirst.frc.team5818.robot.commands.SetPunchTurret;
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
import org.usfirst.frc.team5818.robot.utils.Buttons;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.SchedulerAccess;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Scheduler;

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

        Buttons.initialize();

        dMode = DriveMode.POWER;
        driveCalc = RadiusDriveCalculator.INSTANCE;
    }

    public void setupTeleopButtons() {
        clearButtons();

        Button twoGearButton = Buttons.FW_BACK.get(1);
        twoGearButton.whenPressed(new TwoGearAuto());

        Button getGear = Buttons.FW_BACK.get(2);
        getGear.whenPressed(new AutoSegment(Direction.BACKWARD, Side.LEFT, null));

        Button shiftLow = Buttons.FW_BACK.get(3);
        shiftLow.whenPressed(new ShiftGears(BotConstants.LOW_GEAR_VALUE));

        Button shiftHigh = Buttons.FW_BACK.get(4);
        shiftHigh.whenPressed(new ShiftGears(BotConstants.HIGH_GEAR_VALUE));

        Button expLo = Buttons.TURN.get(3);
        expLo.whenPressed(new ExposureLow());

        Button expHi = Buttons.TURN.get(5);
        expHi.whenPressed(new ExposureHigh());

        Button gear = Buttons.TURN.get(4);
        gear.whenPressed(new GearMode());

        Button tape = Buttons.TURN.get(6);
        tape.whenPressed(new TapeMode());

        Button placeGear = Buttons.TURRET.get(1);
        placeGear.whenPressed(new SetPunchTurret(true));
        placeGear.whenReleased(new SetPunchTurret(false));

        Button turretMinus90 = Buttons.TURRET.get(4);
        turretMinus90.whenPressed(new SetTurretAngle(-90.0));

        Button turretAim = Buttons.TURRET.get(3);
        turretAim.whenPressed(new SetTurretAngle(-0.0));

        Button turretZero = Buttons.TURRET.get(5);
        turretZero.whenPressed(new SetTurretAngle(90.0));

        Button extend = Buttons.TURRET.get(2);
        extend.whenPressed(new SetExtendTurret(true));
        extend.whenReleased(new SetExtendTurret(false));

        Button collect = Buttons.COLLECTOR.get(1);
        collect.whenPressed(new CollectGear());

        Button spit = Buttons.COLLECTOR.get(2);
        spit.whileHeld(new SetCollectorPower(true));

        Button armCollect = Buttons.COLLECTOR.get(4);
        armCollect.whenPressed(new SetCollectorAngle(Collector.COLLECT_POSITION));

        Button armMid = Buttons.COLLECTOR.get(3);
        armMid.whenPressed(new SetCollectorAngle(Collector.MID_POSITION));

        Button armLoad = Buttons.COLLECTOR.get(5);
        armLoad.whenPressed(new SetCollectorAngle(Collector.LOAD_POSITION));

        Button eject = Buttons.COLLECTOR.get(6);
        eject.whenPressed(new SetCollectorPower(false));
    }

    public void setupTestButtons() {
        clearButtons();

        // DriveTrain Talons 1-6
        for (int i = 0; i < 6; i++) {
            Buttons.FW_BACK.get(i + 1)
                    .whenPressed(new ControlMotor(inverted(JS_FW_BACK::getY), new CANTalon(RobotMap.DRIVE_TALONS[i])));
        }
        // Turret Talon 7
        Buttons.TURRET.get(1).whenPressed(new ControlMotor(JS_TURRET::getX, new CANTalon(RobotMap.TURR_MOTOR)));
        // Arm Talons 8 & 9
        final CANTalon left = new CANTalon(RobotMap.ARM_TALON_L);
        final CANTalon right = new CANTalon(RobotMap.ARM_TALON_R);
        Buttons.COLLECTOR.get(1).whenPressed(new ControlMotor(inverted(JS_COLLECTOR::getY), i -> {
            left.pidWrite(i);
            right.pidWrite(i);
        }));
        // Rollers Talons 10 & 11
        Buttons.COLLECTOR.get(2).whenPressed(
                new ControlMotor(inverted(JS_COLLECTOR::getY), new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER)));
        Buttons.COLLECTOR.get(3).whenPressed(
                new ControlMotor(inverted(JS_COLLECTOR::getY), new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER)));
        // Climber Talons 12-15
        for (int i = 0; i < 4; i++) {
            // add 3 for arm/roll motors and one for the correct button offset
            int jsButton = 3 + i + 1;
            Buttons.COLLECTOR.get(jsButton).whenPressed(
                    new ControlMotor(inverted(JS_COLLECTOR::getY), new CANTalon(RobotMap.CLIMB_TALONS[i])));
        }
    }

    private DoubleSupplier inverted(DoubleSupplier input) {
        return () -> -input.getAsDouble();
    }

    public void teleopPeriodic() {
    }

    private void clearButtons() {
        SchedulerAccess.getButtons(Scheduler.getInstance()).clear();
    }

}
