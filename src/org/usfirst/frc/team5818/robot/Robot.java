
package org.usfirst.frc.team5818.robot;

import org.usfirst.frc.team5818.robot.autos.NotPeteyTwoGearAuto;
import org.usfirst.frc.team5818.robot.autos.OneGearButFromTwoGearAuto;
import org.usfirst.frc.team5818.robot.autos.PitTestSideGear;
import org.usfirst.frc.team5818.robot.autos.ProfileSideGear;
import org.usfirst.frc.team5818.robot.autos.SidePegAuto;
import org.usfirst.frc.team5818.robot.autos.SlowTwoGearAuto;
import org.usfirst.frc.team5818.robot.autos.TestProfileCurves;
import org.usfirst.frc.team5818.robot.autos.ProfileTwoGear;
import org.usfirst.frc.team5818.robot.autos.ScrapAuto;
import org.usfirst.frc.team5818.robot.autos.ThreeGearAuto;
import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.RequireAllSubsystems;
import org.usfirst.frc.team5818.robot.commands.ScanForTarget;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.TurretMoveToZero;
import org.usfirst.frc.team5818.robot.commands.fromscratch.PlaceGearForAndrew;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
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
    public TurretMoveToZero turretZero;
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
        turretZero = new TurretMoveToZero();
        requireAllSubsystems = new RequireAllSubsystems();
        //Old Autos -- Same as Ventura
        chooser.addObject("Do Nothing Auto", new TimedCommand(15));
        chooser.addObject("One Gear Auto From Two Gear", new OneGearButFromTwoGearAuto());
        chooser.addObject("Three Gear Auto", new ThreeGearAuto());
        chooser.addObject("Place With Limit -- Andrew", new PlaceGearForAndrew(0.5));
        chooser.addObject("Center Two Gear (Gear Right)", new SlowTwoGearAuto());
        chooser.addObject("Center Two Gear (Gear Left)", new NotPeteyTwoGearAuto());
        chooser.addObject("Side Gear Auto (Bot Left)", new SidePegAuto(60, Side.RIGHT));
        chooser.addObject("Side Gear Auto (Bot Right)", new SidePegAuto(60, Side.LEFT));
        
        //Profile Testing Routines
        chooser.addObject("Test Profile Drive", new DriveTrajectory(70, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        chooser.addObject("Test Backward Profile Drive", new DriveTrajectory(70, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        chooser.addObject("Test Profile Spin", new SpinWithProfile(Math.PI/2.0, true, false));
        chooser.addObject("Test Profile Curving", new TestProfileCurves());
        chooser.addObject("VisionSpin", new SpinWithProfileVision(true, Camera.CAM_GEARS));
        chooser.addObject("Pit-Testable Side Gear", new PitTestSideGear());
        chooser.addObject("Scrap", new ScrapAuto());
        
        //Profiled Autos
        chooser.addObject("Profile Side Gear Bot-Right", new ProfileSideGear(Side.RIGHT));
        chooser.addObject("Profile Side Gear Bot-Left", new ProfileSideGear(Side.LEFT));
        chooser.addObject("Profile Two Gear mid-left", new ProfileTwoGear(85,Side.LEFT));
        chooser.addObject("Profile Two Gear mid-right", new ProfileTwoGear(85,Side.RIGHT));



        

        //
        // chooser.addObject("Side Gear Auto (Bot TEST)", new
        // SidePegAutoTest(180, Side.LEFT));
        // chooser.addObject("SPIN (60)", new ScrapAuto(60));
        // chooser.addObject("SPIN (180)", new ScrapAuto(60));
        // chooser.addObject("Side Two Gear Auto (Right)", new
        // SidePegTwoGear());
        // chooser.addObject("Side One Gear Auto (Left)", new
        // SidePegOneGear(Side.LEFT));
        // chooser.addObject("Side One Gear Auto (Right)", new
        // SidePegOneGear(Side.RIGHT));
        SmartDashboard.putData("Auto mode", chooser);
        vision.start();
        SmartDashboard.putNumber("RIArmAngle", arm.getPosition());

        //
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
        autonomousCommand = chooser.getSelected();

        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */

        // schedule the autonomous command (example)
        driveTrain.shiftGears(Gear.LOW);
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
        driveTrain.getLeftSide().resetEnc();
        driveTrain.getRightSide().resetEnc();
        driveTrain.shiftGears(Gear.LOW);
        driver.setupTeleopButtons();
        camCont.enterGearMode();
        vision.setLightsOn(false);
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
        SmartDashboard.putBoolean("Turret Limit Switch", turret.getLimit());
        SmartDashboard.putBoolean("Collector Limit Switch", collect.isLimitTriggered());
        SmartDashboard.putNumber("Gyro heading", driveTrain.getGyroHeading());
        SmartDashboard.putNumber("Gear X:", vision.getCurrentAngle());
        SmartDashboard.putNumber("Turret Pot:", turret.getRawCounts());
        SmartDashboard.putNumber("Turret Angle:", turret.getAngle());
        SmartDashboard.putNumber("Bot Current", collect.getBotCurrent());
        SmartDashboard.putNumber("Arm Pos", arm.getPosition());
        SmartDashboard.putNumber("left drive encoder", driveTrain.getLeftSide().getSidePosition());
        SmartDashboard.putNumber("right drive encoder", driveTrain.getRightSide().getSidePosition());
        SmartDashboard.putNumber("left encoder raw", driveTrain.getLeftSide().getRawPos());
        SmartDashboard.putNumber("right encoder raw", driveTrain.getRightSide().getRawPos());
        SmartDashboard.putNumber("leftVel", driveTrain.getLeftSide().getSideVelocity());
        SmartDashboard.putNumber("rightVel", driveTrain.getRightSide().getSideVelocity());
    }
}
