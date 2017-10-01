package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * One gear for a specific side, that then drives down field.
 */
public class DownFieldOneGear extends CommandGroup {

    private TwoGearSegment moveForward;
    private TapeMode tapeMode;

    public DownFieldOneGear(Side side) {
        double angMult;
        double sideDist;
        /// FOR BLUE SIDE
        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
            if (side == Side.LEFT) {
                angMult = 1;
                sideDist = 80;
            } else {
                angMult = -1;
                sideDist = 120;
            }
        } else {
            if (side == Side.RIGHT) {
                angMult = 1;
                sideDist = 80;
            } else {
                angMult = -1;
                sideDist = 120;
            }
        }

        setInterruptible(false);
        tapeMode = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode);
        this.addSequential(moveForward);
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());

        this.addSequential(new DriveTrajectory(40, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(angMult * Math.toRadians(80), true, true));
        this.addSequential(new DriveTrajectory(sideDist, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(angMult * Math.PI, true, true));
        this.addSequential(new DriveTrajectory(370, angMult * Math.PI, 0.0, 0.0, Direction.FORWARD, true));

    }

}
