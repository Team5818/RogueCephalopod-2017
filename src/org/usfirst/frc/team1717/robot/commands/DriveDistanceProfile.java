package org.usfirst.frc.team1717.robot.commands;

import org.usfirst.frc.team1717.robot.Robot;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1717.robot.subsystems.DriveTrainSide;
import edu.wpi.first.wpilibj.command.Command;
import utils.LinearLookupTable;

public class DriveDistanceProfile extends Command {

    double dist;
    double timeout;
    double initialLeft;
    double initialRight;
    double initialDist;
    private LinearLookupTable leftTable;
    private LinearLookupTable rightTable;
    private DriveTrain train;
    private DriveTrainSide leftSide;
    private DriveTrainSide rightSide;
    
    public DriveDistanceProfile(LinearLookupTable left, LinearLookupTable right, double distanceInches, double timeoutSeconds) {
        requires(Robot.runningrobot.driveTrain);
        train = Robot.runningrobot.driveTrain;
        leftSide = Robot.runningrobot.driveTrain.getLeftSide();
        rightSide = Robot.runningrobot.driveTrain.getRightSide();
        dist = distanceInches;
        timeout = timeoutSeconds;
        leftTable = left;
        rightTable = right;
    }
    
    public DriveDistanceProfile(LinearLookupTable table, double distance, double timeout){
        this(table, table, distance, timeout);
    }
    
    protected void end()
    {
        leftSide.setPower(0.0);
        rightSide.setPower(0.0);
    }
    
    @Override
    protected void initialize() {
        this.setTimeout(timeout);
        Robot.runningrobot.driveTrain.driveDistance(dist);
        initialDist = Robot.runningrobot.driveTrain.getAverageDistance();
        initialLeft = leftSide.getSidePosition();
        initialRight = rightSide.getSidePosition();
    }

    @Override
    protected void execute() {
        double avDist = train.getAverageDistance() - initialDist;
        double leftPower = leftTable.getEstimate(avDist);
        double rightPower = rightTable.getEstimate(avDist);
        leftSide.setMaxPower(leftPower);
        rightSide.setMaxPower(rightPower);
    }

    @Override
    protected boolean isFinished() {
    	return isTimedOut() || (leftSide.getDistController().onTarget() && rightSide.getDistController().onTarget());
    }

    @Override
    protected void interrupted() {
        this.end();
        
    }
}