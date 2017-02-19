package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.TapeMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

    private AutoSegment forward;
    private AutoSegment getGear;
    private AutoSegment placeGear;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public TwoGearAuto() {
        tapeMode1 = new TapeMode();
        forward = new AutoSegment(Direction.FORWARD, Side.CENTER);
        gearMode = new GearMode();
        getGear = new AutoSegment(Direction.BACKWARD, Side.LEFT);
        tapeMode2 = new TapeMode();
        placeGear = new AutoSegment(Direction.FORWARD, Side.LEFT);

        this.addSequential(tapeMode1);
        this.addSequential(forward);
        this.addSequential(gearMode);
        this.addSequential(getGear);
        this.addSequential(tapeMode2);
        this.addSequential(placeGear);
    }

}