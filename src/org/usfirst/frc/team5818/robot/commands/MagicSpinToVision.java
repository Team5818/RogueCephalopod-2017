package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagicSpinToVision extends Command{

    private static final double CENTER_TO_CAMERA = 6.5;
    private static final double INCHES_PER_ROTATION = 100.0;
    private DriveTrain dt;
    private double distance;
    private VisionTracker vis;

    public MagicSpinToVision(double distToTarget) {
        distance = distToTarget;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        vis = Robot.runningRobot.vision;
    }

    @Override
    protected void initialize() {
        dt.getLeftSide().positionControl();
        dt.getRightSide().slaveToOtherSide(true);
        dt.resetEncs();
    }

    @Override
    protected void execute() {
        double diff = MathUtil.calculateVisionAngleRadians(distance, 6.0, Math.toRadians(vis.getCurrentAngle()));
        DriverStation.reportError("" + diff, false);
        double dist = diff / (2.0 * Math.PI) * INCHES_PER_ROTATION;
        SmartDashboard.putNumber("spin dist", dist);
        DriverStation.reportError("" + dist, false);
        dt.getLeftSide().driveDistanceNoReset(dt.getLeftSide().getSidePosition() + dist, 200, 200);
    }

    @Override
    protected boolean isFinished() {
        return MathUtil.calculateVisionAngleRadians(distance, CENTER_TO_CAMERA, Math.toRadians(vis.getCurrentAngle())) < 0.3
                && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2;
    }

    @Override
    protected void end() {
        dt.stop();
    }


}
