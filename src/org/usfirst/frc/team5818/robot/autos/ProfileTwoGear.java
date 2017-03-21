package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfile;
import org.usfirst.frc.team5818.robot.commands.SpinWithProfileVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.ProfileTwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class ProfileTwoGear extends CommandGroup {

    private ProfileTwoGearSegment moveForward;
    private ProfileTwoGearSegment moveToGear;
    private ProfileTwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public ProfileTwoGear(double dist, Side side) {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new ProfileTwoGearSegment(Direction.BACKWARD, dist, Side.CENTER, null);
        gearMode = new GearMode();
        moveToGear = new ProfileTwoGearSegment(Direction.FORWARD, dist, side, AutoExtra.COLLECT);
        tapeMode2 = new TapeMode();
        moveToPeg = new ProfileTwoGearSegment(Direction.BACKWARD, dist, side, AutoExtra.PLACE);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new TimedCommand(0.5));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        // this.addSequential(new TimedCommand(0.5));
        this.addSequential(new PlaceWithLimit());
    }

}