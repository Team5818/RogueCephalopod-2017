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
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.SchedulerAccess;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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

        dMode = DriveMode.POWER;
        driveCalc = RadiusDriveCalculator.INSTANCE;
    }

    public void setupTeleopButtons() {
        clearButtons();

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
        placeGear.whenPressed(new SetPunchTurret(true));
        placeGear.whenReleased(new SetPunchTurret(false));

        JoystickButton turretMinus90 = new JoystickButton(JS_TURRET, 4);
        turretMinus90.whenPressed(new SetTurretAngle(-90.0));

        JoystickButton turretAim = new JoystickButton(JS_TURRET, 3);
        turretAim.whenPressed(new SetTurretAngle(-0.0));

        JoystickButton turretZero = new JoystickButton(JS_TURRET, 5);
        turretZero.whenPressed(new SetTurretAngle(90.0));

        JoystickButton extend = new JoystickButton(JS_TURRET, 2);
        extend.whenPressed(new SetExtendTurret(true));
        extend.whenReleased(new SetExtendTurret(false));

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
    }

    public void setupTestButtons() {
        clearButtons();

        // DriveTrain Talons 1-6
        for (int i = 0; i < 6; i++) {
            new JoystickButton(JS_FW_BACK, i + 1)
                    .whenPressed(new ControlMotor(inverted(JS_FW_BACK::getY), new CANTalon(RobotMap.DRIVE_TALONS[i])));
        }
        // Turret Talon 7
        new JoystickButton(JS_TURRET, 1)
                .whenPressed(new ControlMotor(JS_TURRET::getX, new CANTalon(RobotMap.TURR_MOTOR)));
        // Arm Talons 8 & 9
        final CANTalon left = new CANTalon(RobotMap.ARM_TALON_L);
        final CANTalon right = new CANTalon(RobotMap.ARM_TALON_R);
        new JoystickButton(JS_COLLECTOR, 1).whenPressed(new ControlMotor(inverted(JS_COLLECTOR::getY), i -> {
            left.pidWrite(i);
            right.pidWrite(i);
        }));
        // Rollers Talons 10 & 11
        new JoystickButton(JS_COLLECTOR, 2).whenPressed(
                new ControlMotor(inverted(JS_COLLECTOR::getY), new CANTalon(RobotMap.TOP_COLLECTOR_ROLLER)));
        new JoystickButton(JS_COLLECTOR, 3).whenPressed(
                new ControlMotor(inverted(JS_COLLECTOR::getY), new CANTalon(RobotMap.BOT_COLLECTOR_ROLLER)));
        // Climber Talons 12-15
        for (int i = 0; i < 4; i++) {
            // add 3 for arm/roll motors and one for the correct button offset
            int jsButton = 3 + i + 1;
            new JoystickButton(JS_COLLECTOR, jsButton).whenPressed(
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
