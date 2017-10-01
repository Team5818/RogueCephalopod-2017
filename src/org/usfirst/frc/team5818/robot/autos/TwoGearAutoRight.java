package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * A two-gear auto that is for the gear being placed on the right side.
 */
public class TwoGearAutoRight extends CommandGroup {

    private TwoGearSegment moveForward;
    private TwoGearSegment moveToGear;
    private TwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public TwoGearAutoRight() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.9);

        gearMode = new GearMode();
        moveToGear = new TwoGearSegment(Direction.FORWARD, Side.RIGHT, AutoExtra.COLLECT, -.75);
        tapeMode2 = new TapeMode();
        moveToPeg = new TwoGearSegment(Direction.BACKWARD, Side.RIGHT, AutoExtra.PLACE, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new TimedCommand(.2));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        // Drive Down the Field
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new DriveTrajectory(40, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(-Math.PI / 2.0, true, true));
        this.addSequential(new DriveTrajectory(120, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(-Math.PI / 2.0, true, true));
        this.addSequential(new DriveTrajectory(370, 0.0, 0.0, 0.0, Direction.FORWARD, true));
    }

}
