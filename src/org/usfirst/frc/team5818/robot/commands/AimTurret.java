package org.usfirst.frc.team5818.robot.commands;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.CameraController;
import org.usfirst.frc.team5818.robot.subsystems.Turret;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;

import edu.wpi.first.wpilibj.command.Command;

public class AimTurret extends Command {

    private static final double CAMERA_FOV = Constant.cameraFov();

    public static final double DEGREES_TOLERANCE = 2;

    public static final double distFromTarget = 36; // Estimation in inches
    public static final double cameraOffset = 6;// Estimation in inches

    /**
     * Finds field of vision taking into account camera offset. Assumes distance
     * from the target and uses tangents to find angle Is the assumption worth
     * it? Maybe we want a way to find distance from target? Or just assume
     * camera is at center of turret?
     */
    public static final double realFOV =
            2 * Math.toDegrees(Math.atan2(Math.tan(CAMERA_FOV / 2) * distFromTarget, distFromTarget + cameraOffset));

    private Turret turr;
    private VisionTracker vision;
    private CameraController cont;
    private double degreesOff;

    public AimTurret() {
        turr = Robot.runningRobot.turret;
        vision = Robot.runningRobot.vision;
        cont = Robot.runningRobot.camCont;
        requires(turr.rotator);
        requires(vision);
        requires(cont);
    }

    @Override
    public void initialize() {
        cont.enterTapeMode();
    }

    @Override
    protected void execute() {
        degreesOff = vision.getCurrentAngle();
        turr.setAngle(degreesOff - turr.getAngle());
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(degreesOff - vision.getCurrentAngle()) < DEGREES_TOLERANCE;
    }

}
