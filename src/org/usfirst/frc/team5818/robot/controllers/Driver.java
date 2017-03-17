package org.usfirst.frc.team5818.robot.controllers;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import java.util.Vector;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team5818.robot.TestingTalon;
import org.usfirst.frc.team5818.robot.commands.ArmControlCommand;
import org.usfirst.frc.team5818.robot.commands.CoRiverControlCommand;
import org.usfirst.frc.team5818.robot.commands.ControlMotor;
import org.usfirst.frc.team5818.robot.commands.DriveControlCommand;
import org.usfirst.frc.team5818.robot.commands.FullExtention;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.MoveArmCollect;
import org.usfirst.frc.team5818.robot.commands.PutGearInTurret;
import org.usfirst.frc.team5818.robot.commands.SetArmAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpitGear;
import org.usfirst.frc.team5818.robot.commands.StartClimbControlCommand;
import org.usfirst.frc.team5818.robot.commands.SwitchDriveMode;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.DriveMode;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.utils.ArcadeDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.Buttons;
import org.usfirst.frc.team5818.robot.utils.DriveCalculator;
import org.usfirst.frc.team5818.robot.utils.RadiusDriveCalculator;
import org.usfirst.frc.team5818.robot.utils.SchedulerAccess;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
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
        JS_FW_BACK = new Joystick(Constant.joystickForwardBack());
        JS_TURN = new Joystick(Constant.joystickTurn());
        JS_TURRET = new Joystick(Constant.joystickTurret());
        JS_COLLECTOR = new Joystick(Constant.joystickCollector());

        dMode = DriveMode.POWER;
        driveCalc = RadiusDriveCalculator.INSTANCE;
        // init talons in TestingTalon
        System.err.println(TestingTalon.class.getName());
    }

    public void setupTeleopButtons() {
        clearButtons();

        Button driverControl = Buttons.FW_BACK.get(1);
        driverControl.whenPressed(new DriveControlCommand(JS_FW_BACK, JS_TURN));

        Button switchDriveMode = Buttons.FW_BACK.get(7);
        switchDriveMode.whenPressed(new SwitchDriveMode(ArcadeDriveCalculator.INSTANCE));
        switchDriveMode.whenReleased(new SwitchDriveMode(RadiusDriveCalculator.INSTANCE));

        Button collectGear = Buttons.TURN.get(1);
        collectGear.whenPressed(new MoveArmCollect());

        Button raiseArm = Buttons.TURN.get(2);
        raiseArm.whenPressed(new SetArmAngle(Arm.MID_POSITION));

        Button shiftLow = Buttons.TURN.get(8);
        shiftLow.whenPressed(new ShiftGears(Gear.LOW));

        Button shiftHigh = Buttons.TURN.get(5);
        shiftHigh.whenPressed(new ShiftGears(Gear.HIGH));

        // Remove After Testing Complete
        // Button collectTest = Buttons.TURN.get(8);
        // collectTest.whenPressed(new CollectGear());
        //
        // Button loadTest = Buttons.TURN.get(5);
        // loadTest.whileHeld(new SetCollectorPower(true, .7, 1000));
        // Remove After Testing Complete

        Button spitGear = Buttons.TURN.get(7);
        spitGear.whileHeld(new SpitGear());

        // REMOVE AFTER TESTING COMPLETE
        // Button armLowTest = Buttons.TURN.get(4);
        // armLowTest.whenPressed(new SetArmAngle(Arm.COLLECT_POSITION));
        //
        // Button armMidTest = Buttons.TURN.get(3);
        // armMidTest.whenPressed(new SetArmAngle(Arm.MID_POSITION));
        //
        // Button armHighTest = Buttons.TURN.get(6);
        // armHighTest.whenPressed(new SetArmAngle(Arm.LOAD_POSITION));
        // REMOVE AFTER TESTING COMPLETE

        Button manualArm = Buttons.TURRET.get(8);
        manualArm.whenPressed(new ArmControlCommand(JS_COLLECTOR));
        manualArm.whenReleased(new SetArmAngle(Arm.MID_POSITION));

        Button tape = Buttons.TURRET.get(7);
        tape.whenPressed(new TapeMode());
        tape.whenReleased(new GearMode());

        Button climbMode = Buttons.TURRET.get(5);
        climbMode.whenPressed(new StartClimbControlCommand());

        Button codriverControl = Buttons.TURRET.get(1);
        codriverControl.whenPressed(new CoRiverControlCommand(JS_COLLECTOR));

        Button extendTurret = Buttons.TURRET.get(2);
        extendTurret.whenPressed(new SetExtendTurret(true));
        extendTurret.whenReleased(new FullExtention(false));

        Button coDriverMidArm = Buttons.COLLECTOR.get(1);
        coDriverMidArm.whenPressed(new SetArmAngle(Arm.MID_POSITION));

        Button turretMinus90 = Buttons.COLLECTOR.get(5);
        turretMinus90.whenPressed(new SetTurretAngle(-60.0));

        Button turretZero = Buttons.COLLECTOR.get(4);
        turretZero.whenPressed(new SetTurretAngle(-0.0));

        Button turret90 = Buttons.COLLECTOR.get(3);
        turret90.whenPressed(new SetTurretAngle(60.0));

        Button deploy = Buttons.COLLECTOR.get(8);
        deploy.whenPressed(new PlaceWithLimit());

        Button fullExtend = Buttons.COLLECTOR.get(7);
        fullExtend.whenPressed(new FullExtention(true));
        fullExtend.whenReleased(new FullExtention(false));

        Button loadGear = Buttons.COLLECTOR.get(6);
        loadGear.whenPressed(new PutGearInTurret());
        loadGear.whenReleased(new SetCollectorPower(false, 0, 0.5));
        // loadGear.whenReleased(new SetArmAngle(Arm.MID_POSITION));
    }

    public void setupTestButtons() {
        clearButtons();

        // DriveTrain Talons 1-6
        for (int i = 0; i < 6; i++) {
            Buttons.FW_BACK.get(i + 1)
                    .whenPressed(new ControlMotor(inverted(JS_FW_BACK::getY), TestingTalon.DRIVE[i].talon));
        }
        // Turret Talon 7
        Buttons.TURRET.get(1).whenPressed(new ControlMotor(JS_TURRET::getX, TestingTalon.TURRET.talon));
        // Arm Talons 8 & 9
        final CANTalon left = TestingTalon.LEFT_ARM.talon;
        final CANTalon right = TestingTalon.RIGHT_ARM.talon;
        DoubleSupplier collectorY = inverted(JS_COLLECTOR::getY);
        Buttons.COLLECTOR.get(1).whenPressed(new ControlMotor(collectorY, i -> {
            left.pidWrite(i);
            right.pidWrite(i);
        }));
        // Rollers Talons 10 & 11
        final CANTalon top = TestingTalon.TOP_ROLLER.talon;
        final CANTalon bot = TestingTalon.BOT_ROLLER.talon;
        Buttons.COLLECTOR.get(2).whenPressed(new ControlMotor(collectorY, i -> {
            top.pidWrite(i);
            bot.pidWrite(i);
        }));
        // Climber Talons 12-15
        for (int i = 0; i < 4; i++) {
            // add 3 for arm/roll motors and one for the correct button offset
            int jsButton = 3 + i + 1;
            Buttons.COLLECTOR.get(jsButton).whenPressed(new ControlMotor(collectorY, TestingTalon.CLIMB[i].talon));
        }
    }

    private DoubleSupplier inverted(DoubleSupplier input) {
        return () -> -input.getAsDouble();
    }

    public void teleopPeriodic() {
    }

    private void clearButtons() {
        Buttons.setButtonMapMode();
        Vector<ButtonScheduler> buttons = SchedulerAccess.getButtons(Scheduler.getInstance());
        if (buttons == null) {
            return;
        }
        buttons.clear();
    }

}
