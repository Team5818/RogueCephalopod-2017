package org.usfirst.frc.team5818.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForwardBackPID extends CommandGroup {

    private DrivePIDDistance driveForward;
    private DrivePIDDistance driveBackward;

    public DriveForwardBackPID(double inches, double timeout) {
        driveForward = new DrivePIDDistance(inches, timeout);
        driveBackward = new DrivePIDDistance(-inches, timeout);
        this.addSequential(driveForward);
        this.addSequential(driveBackward);
    }

}
