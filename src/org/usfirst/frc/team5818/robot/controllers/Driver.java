/*
 * This file is part of Rogue-Cephalopod, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.usfirst.frc.team5818.robot.controllers;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import java.util.Vector;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team5818.robot.TestingTalon;
import org.usfirst.frc.team5818.robot.autos.MagicTapeSpin;
import org.usfirst.frc.team5818.robot.commands.ArmControlCommand;
import org.usfirst.frc.team5818.robot.commands.ControlMotor;
import org.usfirst.frc.team5818.robot.commands.DriveControlCommand;
import org.usfirst.frc.team5818.robot.commands.FullExtention;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.MoveArmCollect;
import org.usfirst.frc.team5818.robot.commands.PutGearInTurret;
import org.usfirst.frc.team5818.robot.commands.SetArmAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
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
import org.usfirst.frc.team5818.robot.subsystems.Turret;
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

/**
 * A collection of related constants and methods related to the primary driver
 * command input.
 */
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

        Button spitGear = Buttons.TURN.get(7);
        spitGear.whileHeld(new SpitGear());

        Button manualArm = Buttons.TURRET.get(8);
        manualArm.whenPressed(new ArmControlCommand(JS_COLLECTOR));
        manualArm.whenReleased(new SetArmAngle(Arm.MID_POSITION));

        Button tape = Buttons.TURRET.get(7);
        tape.whenPressed(new TapeMode());
        tape.whenReleased(new GearMode());

        Button climbMode = Buttons.TURRET.get(5);
        climbMode.whenPressed(new StartClimbControlCommand());

        Button deploy = Buttons.TURRET.get(2);
        deploy.whenPressed(new PlaceWithLimit());

        Button loadGear = Buttons.COLLECTOR.get(1);
        loadGear.whenPressed(new PutGearInTurret.Start());
        loadGear.whenReleased(new SetCollectorPower(false, 0, 0.5));

        Button coDriverMidArm = Buttons.COLLECTOR.get(2);
        coDriverMidArm.whenPressed(new SetArmAngle(Arm.MID_POSITION));

        Button turretMinus90 = Buttons.COLLECTOR.get(5);
        turretMinus90.whenPressed(new SetTurretAngle(Turret.TURRET_LEFT_POS));

        Button turretZero = Buttons.COLLECTOR.get(4);
        turretZero.whenPressed(new SetTurretAngle(Turret.TURRET_CENTER_POS));

        Button turret90 = Buttons.COLLECTOR.get(3);
        turret90.whenPressed(new SetTurretAngle(Turret.TURRET_RIGHT_POS));

        Button fullExtend = Buttons.COLLECTOR.get(7);
        fullExtend.whenPressed(new FullExtention(true));
        fullExtend.whenReleased(new FullExtention(false));

        Button coSpit = Buttons.COLLECTOR.get(6);
        coSpit.whileHeld(new SetCollectorPower(false, .4, 1));
        
        Button visSpin = Buttons.TURN.get(3);
        visSpin.whenPressed(new MagicTapeSpin());
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