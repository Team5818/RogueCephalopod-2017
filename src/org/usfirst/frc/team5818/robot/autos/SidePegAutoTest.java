package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.SetArmAngle;
import org.usfirst.frc.team5818.robot.commands.SetCollectorPower;
import org.usfirst.frc.team5818.robot.commands.SpinWithVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TurretSmallAdjustment;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.constants.Spin;
import org.usfirst.frc.team5818.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class SidePegAutoTest extends CommandGroup {

    public SidePegAutoTest(double angle, Side side) {
        Spin spin;
        if (side == Side.LEFT) {
            spin = Spin.COUNTERCW;
        } else {
            spin = Spin.CLOCKWISE;
        }
        final double initDrive = 60;
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(initDrive);
            b.maxPower(.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(new SpinWithVision(angle, 20, spin, Camera.CAM_TAPE));
        addSequential(DriveAtRatio.withSpin(b -> {
            b.angle(10);
            b.maxPower(.5);
            b.rotation(spin);
            b.stoppingAtEnd(true);
        }));
        addSequential(new TimedCommand(.5));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(55);
            b.maxPower(.5);
            b.maxRatio(3.0);
            b.stoppingAtEnd(false);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(5);
            b.maxPower(.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(new PlaceWithLimit());

        CommandGroup onDriveBack = new CommandGroup();
        CommandGroup driveToGear = new CommandGroup();
        driveToGear.addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(30);
            b.maxPower(-.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));
        driveToGear.addSequential(DriveAtRatio.withSpin(b -> {
            b.angle(angle);
            b.rotation(spin.theOtherWay());
            b.maxPower(-0.5);
            b.stoppingAtEnd(true);
        }));
        driveToGear.addSequential(DriveAtRatio.withVision(Camera.CAM_GEARS, b -> {
            b.inches(initDrive + 5);
            b.maxPower(-0.5);
            b.maxRatio(2);
            b.stoppingAtEnd(true);
        }));

        onDriveBack.addParallel(driveToGear);

        onDriveBack.addParallel(new SetArmAngle(Arm.COLLECT_POSITION));
        onDriveBack.addParallel(new TurretSmallAdjustment(0.0));
        onDriveBack.addParallel(new CollectGear(1, 5));

        // addSequential(onDriveBack);

        CommandGroup onToPeg = new CommandGroup();
        CommandGroup driveToPeg = new CommandGroup();
        driveToPeg.addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(initDrive);
            b.maxPower(.5);
            b.targetRatio(side.adjustRatio(1.5, Direction.BACKWARD));
            b.stoppingAtEnd(true);
        }));
        driveToPeg.addSequential(DriveAtRatio.withSpin(b -> {
            b.angle(angle);
            b.rotation(spin);
            b.maxPower(.5);
            b.stoppingAtEnd(true);
        }));
        driveToPeg.addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(65.5);
            b.maxPower(.5);
            b.maxRatio(3.0);
            b.stoppingAtEnd(false);
        }));
        driveToPeg.addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(7);
            b.maxPower(.5);
            b.targetRatio(1.0);
            b.stoppingAtEnd(true);
        }));

        onToPeg.addParallel(driveToPeg);
        CommandGroup loadGear = new CommandGroup();
        loadGear.addSequential(new SetArmAngle(Arm.LOAD_POSITION));
        loadGear.addSequential(new SetCollectorPower(true, 1.0, 1.75));
        loadGear.addSequential(new SetArmAngle(Arm.MID_POSITION));
        onToPeg.addParallel(loadGear);

        // addSequential(onToPeg);
        // addSequential(new PlaceWithLimit());
    }
}
