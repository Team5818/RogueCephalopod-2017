package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class OneGearButFromTwoGearAuto extends CommandGroup {

    private TwoGearSegment moveForward;
    // private TwoGearSegment moveToGear;
    // private TwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    // private GearMode gearMode;
    // private TapeMode tapeMode2;
    // private UTurn turn;

    public OneGearButFromTwoGearAuto() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.5);
        //
        // gearMode = new GearMode();
        // moveToGear = new TwoGearSegment(Direction.FORWARD, Side.LEFT,
        // AutoExtra.COLLECT, .5);
        // tapeMode2 = new TapeMode();
        // moveToPeg = new TwoGearSegment(Direction.BACKWARD, Side.LEFT,
        // AutoExtra.PLACE, .5);

        // turn = new UTurn(96, 0.7, Side.LEFT);

        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        // this.addSequential(DriveAtRatio.withDeadReckon(b -> {
        // b.inches(80);
        // b.targetRatio(1);
        // b.maxPower(.6);
        // b.stoppingAtEnd(true);
        // }));
        this.addSequential(new TimedCommand(1.0));
        this.addSequential(new PlaceWithLimit());
        // this.addSequential(gearMode);
        // this.addSequential(moveToGear);
        // this.addSequential(tapeMode2);
        // this.addSequential(moveToPeg);
        // this.addSequential(turn);
    }

}
