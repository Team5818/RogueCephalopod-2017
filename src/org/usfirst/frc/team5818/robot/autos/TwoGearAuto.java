package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.CollectGear;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.PlaceGear;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

    private AutoSegment moveForward;
    private AutoSegment moveToGear;
    private AutoSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public TwoGearAuto() {
        tapeMode1 = new TapeMode();
        moveForward = new AutoSegment(Direction.FORWARD, Side.CENTER, false);
        gearMode = new GearMode();
        moveToGear = new AutoSegment(Direction.BACKWARD, Side.LEFT, true);
        tapeMode2 = new TapeMode();
        moveToPeg = new AutoSegment(Direction.FORWARD, Side.LEFT, false);

        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new PlaceGear());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(new CollectGear());
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        this.addSequential(new PlaceGear());
    }

}
