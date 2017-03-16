package org.usfirst.frc.team5818.robot.commands.driveatratio;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import java.util.function.Consumer;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveAtRatio extends Command {

    public static final double MIN_SONIC_RANGE = 5;
    private static final double CAMERA_FOV = Constant.cameraFov();

    public static DriveAtRatio withDeadReckon(Consumer<DeadReckonOpts.Builder> config) {
        DeadReckonOpts.Builder b = DeadReckonOpts.builder();
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    public static DriveAtRatio withProfile(Consumer<ProfileOpts.Builder> config) {
        ProfileOpts.Builder b = ProfileOpts.builder();
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    public static DriveAtRatio withVision(Camera camera, Consumer<VisionOpts.Builder> config) {
        VisionOpts.Builder b = VisionOpts.builder(camera);
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    public static DriveAtRatio withSpin(Consumer<SpinOpts.Builder> config) {
        SpinOpts.Builder b = SpinOpts.builder();
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    // fast
    public static DriveAtRatio withSanic(Consumer<SanicOpts.Builder> config) {
        SanicOpts.Builder b = SanicOpts.builder();
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    private double inches;
    private double maxPow;
    private double leftPowMult;
    private double rightPowMult;
    private double leftVel;
    private double rightVel;
    private double avStart;
    private double targetRatio;
    private boolean stopAtEnd;
    private int camMultiplier;
    private boolean useVision;
    private double maxRatio;
    private CameraController cont;
    private Camera camera;
    private boolean useSanic;
    private boolean useSpin;
    private Side spinSide;
    private double leftSpinMult;
    private double rightSpinMult;
    private double powerSlope;
    private double minPower;
    private boolean isProfiling;

    private DriveAtRatio(DriveAtRatioOptions opts) {
        isProfiling = opts.isProfiling();
        minPower = opts.getMinPower();
        powerSlope = opts.getAccel();
        useSpin = opts.isSpinning();
        spinSide = opts.getRotation();
        camera = opts.getCamera();
        inches = opts.getInches();
        maxPow = opts.getMaxPower();
        requires(Robot.runningRobot.driveTrain);
        cont = Robot.runningRobot.camCont;
        requires(cont);
        setTimeout(inches / 12);
        targetRatio = opts.getTargetRatio(); // Ratio is LEFT/RIGHT
        maxRatio = opts.getMaxRatio();
        if (camera.equals(Camera.NONE)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = false;
        } else if (camera.equals(Camera.CAM_GEARS)) {
            camMultiplier = 1;
            maxPow = -Math.abs(maxPow);
            useVision = true;
            useSanic = false;
        } else if (camera.equals(Camera.CAM_TAPE)) {
            camMultiplier = -1;
            maxPow = Math.abs(maxPow);
            useVision = true;
            useSanic = false;
        } else if (camera.equals(Camera.ULTRASANIC)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = true;

        }

        if (spinSide.equals(Side.LEFT)) {
            leftSpinMult = -1;
            rightSpinMult = 1;
        } else if (spinSide.equals(Side.RIGHT)) {
            leftSpinMult = 1;
            rightSpinMult = -1;
        } else {
            leftSpinMult = 1;
            rightSpinMult = 1;
        }

        stopAtEnd = opts.isStoppingAtEnd();
    }

    @Override
    public void initialize() {
        DriverStation.reportError("Begining the drive", false);
        SmartDashboard.putNumber("Vision Angle", Robot.runningRobot.vision.getCurrentAngle());
        leftPowMult = 1;
        rightPowMult = 1;
        if (useSpin) {
            avStart = Robot.runningRobot.driveTrain.getAbsAverageDistance();
        } else {
            avStart = Robot.runningRobot.driveTrain.getAverageDistance();
        }

        if (camera.equals(Camera.CAM_TAPE)) {
            cont.enterTapeMode();
        } else if (camera.equals(Camera.CAM_GEARS)) {
            cont.enterGearMode();
        }
    }

    @Override
    public void execute() {
        leftVel = Math.abs(Robot.runningRobot.driveTrain.left.getSideVelocity());
        rightVel = Math.abs(Robot.runningRobot.driveTrain.right.getSideVelocity());
        double distance = Robot.runningRobot.driveTrain.getAverageDistance();
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }

        double anglePower = 0.0;
        if (!Double.isNaN(Robot.runningRobot.vision.getCurrentAngle())) {
            anglePower = Robot.runningRobot.vision.getCurrentAngle() / CAMERA_FOV * camMultiplier * 2.0;
        }

        double target = targetRatio;

        if (useVision) {
            target = Math.pow(maxRatio, anglePower);
        } else {
            target = Math.pow(target, Math.signum(maxPow));
        }

        leftPowMult = 1.0;
        rightPowMult = currRatio / Math.pow(target, 1);

        Vector2d driveVec = new Vector2d(leftSpinMult * leftPowMult, rightSpinMult * rightPowMult);
        if (isProfiling){ 
            driveVec = driveVec.normalize(Math.min(minPower + powerSlope * distance, maxPow));
        } else {
            driveVec = driveVec.normalize(maxPow);
        }

        SmartDashboard.putNumber("PowerLeft", driveVec.getX());
        SmartDashboard.putNumber("PowerRight", driveVec.getY());
        Robot.runningRobot.driveTrain.setPowerLeftRight(driveVec);

    }

    @Override
    public void end() {
        if (stopAtEnd) {
            Robot.runningRobot.driveTrain.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        final DriveTrain dt = Robot.runningRobot.driveTrain;
        double distance;
        if (useSpin) {
            distance = dt.getAbsAverageDistance();
        } else {
            distance = dt.getAverageDistance();
        }

        boolean passedTarget = Math.abs(distance - avStart) >= Math.abs(inches);
        if (useSanic) {
            boolean sonicThresh = Robot.runningRobot.driveTrain.readSanic() < MIN_SONIC_RANGE;
            return isTimedOut() || passedTarget || sonicThresh;
        }
        return isTimedOut() || passedTarget;
    }

}
