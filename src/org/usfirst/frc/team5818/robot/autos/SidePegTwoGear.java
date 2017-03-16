package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.SideTwoGearSegment;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Gear;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class SidePegTwoGear extends CommandGroup {

    private SideTwoGearSegment moveForward;
    private SideTwoGearSegment moveToGear;
    private SideTwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public SidePegTwoGear() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new SideTwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.95);

        gearMode = new GearMode();
        moveToGear = new SideTwoGearSegment(Direction.FORWARD, Side.RIGHT, AutoExtra.COLLECT, -.75);
        tapeMode2 = new TapeMode();
        moveToPeg = new SideTwoGearSegment(Direction.BACKWARD, Side.RIGHT, AutoExtra.PLACE, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode1);
        this.addSequential(new SetTurretAngle(30.0));
        this.addSequential(moveForward);
        this.addSequential(new TimedCommand(1.0));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        this.addSequential(new SetTurretAngle(30.0));
        this.addSequential(new TimedCommand(1.0));
        this.addSequential(new PlaceWithLimit());
    }
}
