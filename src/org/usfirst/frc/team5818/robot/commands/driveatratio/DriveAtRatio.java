package org.usfirst.frc.team5818.robot.commands.driveatratio;

import java.util.function.Consumer;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveAtRatio extends Command {

    public static final double MIN_SONIC_RANGE = 5;

    public static DriveAtRatio withDeadReckon(Consumer<DeadReckonOpts.Builder> config) {
        DeadReckonOpts.Builder b = DeadReckonOpts.builder();
        config.accept(b);
        return new DriveAtRatio(b.build());
    }

    public static DriveAtRatio withVision(Camera camera, Consumer<VisionOpts.Builder> config) {
        VisionOpts.Builder b = VisionOpts.builder(camera);
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

    private DriveAtRatio(DriveAtRatioOptions opts) {
        camera = opts.getCamera();
        inches = opts.getInches();
        maxPow = opts.getMaxPower();
        requires(Robot.runningrobot.driveTrain);
        cont = Robot.runningrobot.camCont;
        requires(cont);
        setTimeout(inches / 12);
        targetRatio = opts.getTargetRatio(); // Ratio is LEFT/RIGHT
        maxRatio = opts.getMaxRatio();
        if (camera.equals(Camera.NONE)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = false;
        } else if (camera.equals(Camera.CAM_FORWARD)) {
            camMultiplier = 1;
            maxPow = Math.abs(maxPow);
            useVision = true;
            useSanic = false;
        } else if (camera.equals(Camera.CAM_BACKWARD)) {
            camMultiplier = -1;
            maxPow = -Math.abs(maxPow);
            useVision = true;
            useSanic = false;
        } else if (camera.equals(Camera.ULTRASANIC)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = true;

        }

        stopAtEnd = opts.isStoppingAtEnd();
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Vision Angle", Robot.runningrobot.track.getCurrentAngle());
        leftPowMult = 1;
        rightPowMult = 1;
        avStart = Robot.runningrobot.driveTrain.getAverageDistance();

        if (camera.equals(Camera.CAM_FORWARD)) {
            cont.enterTapeMode();
        } else if (camera.equals(Camera.CAM_BACKWARD)) {
            cont.enterGearMode();
        }
    }

    @Override
    public void execute() {
        leftVel = Math.abs(Robot.runningrobot.driveTrain.left.getSideVelocity());
        rightVel = Math.abs(Robot.runningrobot.driveTrain.right.getSideVelocity());
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }

        double anglePower = Robot.runningrobot.track.getCurrentAngle() / BotConstants.CAMERA_FOV * camMultiplier * 2.0;

        double target = targetRatio;

        if (useVision) {
            target = Math.pow(maxRatio, anglePower);
        } else {
            target = Math.pow(target, Math.signum(maxPow));
        }

        leftPowMult = 1.0;
        rightPowMult = currRatio / target;

        Vector2d driveVec = new Vector2d(leftPowMult, rightPowMult);
        driveVec = driveVec.normalize(maxPow);

        Robot.runningrobot.driveTrain.setPowerLeftRight(driveVec);

    }

    @Override
    public void end() {
        if (stopAtEnd) {
            Robot.runningrobot.driveTrain.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        boolean passedTarget =
                Math.abs(Robot.runningrobot.driveTrain.getAverageDistance() - avStart) >= Math.abs(inches);
        if (useSanic) {
            boolean sonicThresh = Robot.runningrobot.driveTrain.readSanic() < MIN_SONIC_RANGE;
            return isTimedOut() || passedTarget || sonicThresh;
        }
        return isTimedOut() || passedTarget;

    }

}
