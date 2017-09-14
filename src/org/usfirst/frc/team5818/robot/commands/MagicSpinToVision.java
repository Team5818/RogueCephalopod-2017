package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5818.robot.subsystems.VisionTracker;
import org.usfirst.frc.team5818.robot.utils.MathUtil;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MagicSpinToVision extends Command {

    private static final double INCHES_PER_ROTATION = 100.0;
    private DriveTrain dt;
    private VisionTracker vis;
    private double angle;

    public MagicSpinToVision(double distToTarget) {
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        vis = Robot.runningRobot.vision;
    }

    @Override
    protected void initialize() {
        dt.getLeftSide().positionControl();
        dt.getRightSide().slaveToOtherSide(true);
        dt.resetEncs();
        double ang = vis.getCurrentAngle();
        if(!Double.isNaN(ang)) {
            angle = dt.getGyroHeading() + Math.toRadians(ang);
         }
        else {
            angle = 0.0;
        }
    }

    @Override
    protected void execute() {
        double diff = MathUtil.wrapAngleRad(angle - dt.getGyroHeading());
        DriverStation.reportError("" + diff, false);
        double dist = diff / (2.0 * Math.PI) * INCHES_PER_ROTATION;
        SmartDashboard.putNumber("spin dist", dist);
        DriverStation.reportError("" + dist, false);
        dt.getLeftSide().driveDistanceNoReset(dt.getLeftSide().getSidePosition() + dist, 200, 200);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(MathUtil.wrapAngleRad(angle - dt.getGyroHeading())) < .03
                && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2;
    }

    @Override
    protected void end() {
        dt.stop();
    }

}
