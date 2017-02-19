package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.BotConstants;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.utils.Vector2d;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Command {

    public static final double MIN_SONIC_RANGE = 5;

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

    public DriveStraight(double in, double pow, double targetRat, double maxRat,
            Camera cam, boolean stop) {
        camera = cam;
        inches = in;
        maxPow = pow;
        requires(Robot.runningrobot.driveTrain);
        setTimeout(in / 12);
        targetRatio = targetRat; // Ratio is LEFT/RIGHT
        maxRatio = maxRat;
        cont = Robot.runningrobot.camCont;
        if (cam.equals(Camera.NONE)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = false;
        } else if (cam.equals(Camera.CAM_FORWARD)) {
            camMultiplier = 1;
            maxPow = Math.abs(pow);
            useVision = true;
            useSanic = false;
        } else if (cam.equals(Camera.CAM_BACKWARD)) {
            camMultiplier = -1;
            maxPow = -Math.abs(pow);
            useVision = true;
            useSanic = false;
        } else if (cam.equals(Camera.ULTRASANIC)) {
            camMultiplier = 0;
            useVision = false;
            useSanic = true;

        }

        stopAtEnd = stop;
    }

    /**
     * No vision, no sanic constructor
     */
    public DriveStraight(double in, double pow, double targetRatio,
            boolean stop) {
        this(in, pow, targetRatio, 1.0, Camera.NONE, stop);
    }

    /**
     * Sanic constructor
     */
    public DriveStraight(double in, double pow, double targetRatio) {
        this(in, pow, targetRatio, 1.0, Camera.ULTRASANIC, true);
    }

    /**
     * Vision Constructor
     */
    public DriveStraight(double in, double pow, double maxRatio, Camera cam,
            boolean stop) {
        this(in, pow, 1.0, maxRatio, cam, stop);
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Vision Angle",
                Robot.runningrobot.track.getCurrentAngle());
        leftPowMult = 1;
        rightPowMult = 1;
        avStart = Robot.runningrobot.driveTrain.getAverageDistance();

        if (camera.equals(Camera.CAM_FORWARD)) {
            cont.tapeMode();
        } else if (camera.equals(Camera.CAM_BACKWARD)) {
            cont.gearMode();
        }
    }

    public void execute() {
        leftVel =
                Math.abs(Robot.runningrobot.driveTrain.left.getSideVelocity());
        rightVel =
                Math.abs(Robot.runningrobot.driveTrain.right.getSideVelocity());
        double currRatio = targetRatio;

        if (leftVel != 0 && rightVel != 0) {
            currRatio = leftVel / rightVel;
        }

        double anglePower = Robot.runningrobot.track.getCurrentAngle()
                / BotConstants.CAMERA_FOV * camMultiplier * 2.0;

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
                Math.abs(Robot.runningrobot.driveTrain.getAverageDistance()
                        - avStart) >= Math.abs(inches);
        if (useSanic) {
            boolean sonicThresh =
                    Robot.runningrobot.driveTrain.readSanic() < MIN_SONIC_RANGE;
            return isTimedOut() || passedTarget || sonicThresh;
        }
        return isTimedOut() || passedTarget;

    }

}
