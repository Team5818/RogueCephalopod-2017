package org.usfirst.frc.team5818.robot.commands;

import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ProfileTwoGearSegment extends CommandGroup {

    private CommandGroup drive;
    private CommandGroup whileDriving;
    private Command firstDrive;
    private Command spin;
    private Command visionSpin;
    private Command driveFinal;
    
    private static final double SPIN_DIST = 50;

    public ProfileTwoGearSegment(Direction dir, double gearDist, Side side, AutoExtra extra) {
        drive = new CommandGroup();
        whileDriving = new CommandGroup();
        
        double gearAng;
        if(side == Side.RIGHT){
            gearAng = -45;
        }
        else{
            gearAng = 45;
        }
        
        if (dir.equals(Direction.FORWARD)) {
            firstDrive = new DriveTrajectory(gearDist, 0.0, 0.0, 0.0, Direction.FORWARD, true);
            spin = new SpinWithProfile(0.0, true, false);
            visionSpin = new SpinWithProfileVision(true, Camera.CAM_TAPE);
            driveFinal = new DriveTrajectory(SPIN_DIST, 0.0, 0.0, 0.0, Direction.FORWARD, true);
        } else {
            if (side != Side.CENTER) {
                firstDrive = new DriveTrajectory(SPIN_DIST, 0.0, 0.0, 0.0, Direction.BACKWARD, true);
                spin = new SpinWithProfile(gearAng, true, false);
                visionSpin = new SpinWithProfileVision(true, Camera.CAM_GEARS);
                driveFinal = new DriveTrajectory(gearDist, 0.0, 0.0, 0.0, Direction.BACKWARD, true);
            } else {
                firstDrive = new DriveTrajectory(110, 0.0, 0.0, 0.0, Direction.BACKWARD, true);
                spin = new InstantCommand();
                visionSpin = new InstantCommand();
                driveFinal = new InstantCommand();
            }
        }

        drive.addSequential(firstDrive);
        drive.addSequential(spin);
        drive.addSequential(visionSpin);
        drive.addSequential(driveFinal);


        if (extra == AutoExtra.COLLECT) {
            whileDriving.addSequential((new SetArmAngle(Arm.COLLECT_POSITION)));
            whileDriving.addSequential(new TurretSmallAdjustment(0.0));
            whileDriving.addSequential(new CollectGear(1, 5));
        } else if (extra == AutoExtra.PLACE) {
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
//            whileDriving.addSequential(new SetTurretAngle(0));
            whileDriving.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
            whileDriving.addSequential(new SetCollectorPower(true, 1.0, 1.5));
            whileDriving.addSequential(new SetArmAngle(Arm.MID_POSITION));
        }

        this.addParallel(drive);
        this.addParallel(whileDriving);
    }
}