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
package org.usfirst.frc.team5818.robot;

import org.usfirst.frc.team5818.robot.autos.CenterOneGearAuto;
import org.usfirst.frc.team5818.robot.autos.MagicSideGear;
import org.usfirst.frc.team5818.robot.autos.TwoGearAutoLeft;
import org.usfirst.frc.team5818.robot.autos.TwoGearAutoRight;
import org.usfirst.frc.team5818.robot.commands.RequireAllSubsystems;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.Arm;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.Climber;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrainSide;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static Robot runningRobot;

    public DriveTrain driveTrain;
    public Driver driver;
    public Arm arm;
    public Collector collect;
    public VisionTracker vision;
    public Turret turret;
    public Climber climb;
    public CameraController camCont;
    public SetTurretAngle turretZero;
    public boolean turretSafetyChecks = true;

    private RequireAllSubsystems requireAllSubsystems;

    Command autonomousCommand;
    SendableChooser<Command> chooser;
    DriveTrainSide left;
    DriveTrainSide right;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        /* Statically instantiate all subsystems */
        runningRobot = this;
        driveTrain = new DriveTrain();
        vision = new VisionTracker();
        turret = new Turret();
        collect = new Collector();
        arm = new Arm();
        climb = new Climber();
        chooser = new SendableChooser<>();
        camCont = new CameraController();
        driver = new Driver();
        turretZero = new SetTurretAngle(Turret.TURRET_CENTER_POS);
        requireAllSubsystems = new RequireAllSubsystems();

        /* Old Autos -- Same as Ventura */
        chooser.addObject("Do Nothing Auto", new TimedCommand(15));
        chooser.addObject("Center Two Gear (Gear Right)", new TwoGearAutoRight());
        chooser.addObject("Center Two Gear (Gear Left)", new TwoGearAutoLeft());

        /* Center */
        chooser.addObject("Down Field 1 Gear Right", new CenterOneGearAuto(Side.RIGHT));
        chooser.addObject("Down Field 1 Gear Left", new CenterOneGearAuto(Side.LEFT));

        /*Side Pegs*/
        chooser.addObject("Side Gear Blue Opposite", new MagicSideGear(MagicSideGear.Position.BLUE_OPPOSITE));
        chooser.addObject("Side Gear Blue Boiler", new MagicSideGear(MagicSideGear.Position.BLUE_BOILER));
        chooser.addObject("Side Gear Red Opposite", new MagicSideGear(MagicSideGear.Position.RED_OPPOSITE));
        chooser.addObject("Side Gear Red Boiler", new MagicSideGear(MagicSideGear.Position.RED_BOILER));


        SmartDashboard.putData("Auto mode", chooser);

        /* Put robot in starting configuration */
        vision.start();
        driveTrain.shiftGears(Gear.LOW);
        driveTrain.getGyro().reset();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        if (requireAllSubsystems.isRunning()) {
            requireAllSubsystems.cancel();
        }
        arm.setBrakeMode(true);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        printSmartDash();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {

        driveTrain.getGyro().zeroYaw();
        driveTrain.shiftGears(Gear.HIGH);
        autonomousCommand = chooser.getSelected();
        if (autonomousCommand != null)
            autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printSmartDash();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();

        /* Put robot in starting configuration */
        turret.extend(false);
        turret.punch(false);
        driveTrain.getLeftSide().resetEnc();
        driveTrain.getRightSide().resetEnc();
        driveTrain.shiftGears(Gear.LOW);
        camCont.enterGearMode();
        vision.setLightsOn(false);

        /* Put buttons in teleop mode */
        driver.setupTeleopButtons();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        printSmartDash();
        driver.teleopPeriodic();
        /* check arm for exceeding disable position */
        if (arm.getPosition() >= Arm.TURRET_RESET_POSITION) {
            runTurretOverrides();
        }

        Scheduler.getInstance().run();
    }

    public void runTurretOverrides() {
        if (turretSafetyChecks && !turretZero.isRunning()) {
            turretZero.start();
        }
    }

    @Override
    public void testInit() {
        LiveWindow.run();
        LiveWindow.setEnabled(false);
        requireAllSubsystems.start();
        /* Put buttons in testing mode */
        driver.setupTestButtons();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        printSmartDash();
        Scheduler.getInstance().run();
    }

    public void printSmartDash() {
        /* Print readings from pretty much every sensor */
        SmartDashboard.putBoolean("Turret Limit Switch", turret.getLimit());
        SmartDashboard.putBoolean("Collector Limit Switch", collect.isLimitTriggered());
        SmartDashboard.putNumber("Gyro heading", MathUtil.wrapAngleRad(driveTrain.getGyroHeading()));
        SmartDashboard.putNumber("Gear X:", vision.getCurrentAngle());
        SmartDashboard.putNumber("Turret Pot:", turret.getPositionRaw());
        SmartDashboard.putNumber("Turret Angle:", turret.getPosition());
        SmartDashboard.putNumber("Turret Speed:", turret.getVeleocity());
        SmartDashboard.putNumber("Bot Current", collect.getBotCurrent());
        SmartDashboard.putNumber("Arm Pos", arm.getPosition());
        SmartDashboard.putNumber("Arm Raw", arm.getPositionRaw());
        SmartDashboard.putNumber("Arm Vel", arm.getVelocity());
        SmartDashboard.putNumber("Arm error", arm.getError());
        SmartDashboard.putNumber("left drive encoder", driveTrain.getLeftSide().getSidePosition());
        SmartDashboard.putNumber("right drive encoder", driveTrain.getRightSide().getSidePosition());
        SmartDashboard.putNumber("left encoder raw", driveTrain.getLeftSide().getRawPos());
        SmartDashboard.putNumber("right encoder raw", driveTrain.getRightSide().getRawPos());
        SmartDashboard.putNumber("leftVel", driveTrain.getLeftSide().getSideVelocity());
        SmartDashboard.putNumber("rightVel", driveTrain.getRightSide().getSideVelocity());
        SmartDashboard.putNumber("leftVelRaw", driveTrain.getLeftSide().getRawSpeed());
        SmartDashboard.putNumber("rightVelRaw", driveTrain.getRightSide().getRawSpeed());
        SmartDashboard.putNumber("leftErr", driveTrain.getLeftSide().getSideError());
        SmartDashboard.putNumber("rightErr", driveTrain.getRightSide().getSideError());
        SmartDashboard.putBoolean("GyroCalibrating", driveTrain.getGyro().isCalibrating());
    }
}
