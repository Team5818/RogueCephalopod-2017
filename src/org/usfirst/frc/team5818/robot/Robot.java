
package org.usfirst.frc.team5818.robot;

import org.usfirst.frc.team5818.robot.autos.OneGearAuto;
import org.usfirst.frc.team5818.robot.autos.ThreeGearAuto;
import org.usfirst.frc.team5818.robot.commands.DriveForwardBackPID;
import org.usfirst.frc.team5818.robot.commands.DrivePIDDistance;
import org.usfirst.frc.team5818.robot.commands.RequireAllSubsystems;
import org.usfirst.frc.team5818.robot.commands.TurretMoveToZero;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.controllers.Driver;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.Climber;
import org.usfirst.frc.team5818.robot.subsystems.Collector;
import org.usfirst.frc.team5818.robot.subsystems.CollectorRollers;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrainSide;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
    public Collector collector;
    public CollectorRollers roll;
    public VisionTracker track;
    public Turret turret;
    public Climber climb;
    public CameraController camCont;
    public TurretMoveToZero turretZero;
    
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
        runningRobot = this;
        driveTrain = new DriveTrain();
        track = new VisionTracker();
        turret = new Turret();
        roll = new CollectorRollers();
        collector = new Collector();
        climb = new Climber();
        chooser = new SendableChooser<>();
        camCont = new CameraController();
        driver = new Driver();
        turretZero = new TurretMoveToZero();
        requireAllSubsystems = new RequireAllSubsystems();
        chooser.addObject("One Gear Auto", new OneGearAuto(Side.LEFT));
        chooser.addObject("Three Gear Auto", new ThreeGearAuto());
        SmartDashboard.putData("Auto mode", chooser);
        track.start();
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
        collector.setBrakeMode(true);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
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
        autonomousCommand = chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
            autonomousCommand.start();
        driveTrain.shiftGears(BotConstants.LOW_GEAR_VALUE);
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
        driveTrain.getLeftSide().resetEnc();
        driveTrain.getRightSide().resetEnc();
        driveTrain.shiftGears(BotConstants.LOW_GEAR_VALUE);
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
        if (collector.getPosition() >= Collector.TURRET_RESET_POSITION) {
            runTurretOverrides();
        }
        Scheduler.getInstance().run();
    }

    public void runTurretOverrides() {
        if (!turretZero.isRunning()) {
            turretZero.start();
        }
    }

    @Override
    public void testInit() {
        LiveWindow.run();
        LiveWindow.setEnabled(false);
        requireAllSubsystems.start();
        driver.setupTestButtons();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }

    public void printSmartDash() {
        SmartDashboard.putBoolean("VisDrive", driveTrain.isVisionDriving());
        SmartDashboard.putBoolean("Passed Target", driveTrain.passedTarget());
        SmartDashboard.putBoolean("Turret Limit Switch", turret.getLimit());
        SmartDashboard.putNumber("Gear X:", track.getCurrentAngle());
        SmartDashboard.putNumber("Turret Pot:", turret.getRawCounts());
        SmartDashboard.putNumber("Turret Angle:", turret.getAngle());
        SmartDashboard.putNumber("Sanic Reading:", driveTrain.readSanic());
        SmartDashboard.putNumber("Bot Current", roll.getBotCurrent());
        SmartDashboard.putNumber("Arm Pos", collector.getPosition());
        SmartDashboard.putNumber("left drive encoder", driveTrain.getLeftSide().getSidePosition());
        SmartDashboard.putNumber("right drive encoder", driveTrain.getRightSide().getSidePosition());
        SmartDashboard.putNumber("left encoder raw", driveTrain.getLeftSide().getRawPos());
        SmartDashboard.putNumber("right encoder raw", driveTrain.getRightSide().getRawPos());
    }
}
