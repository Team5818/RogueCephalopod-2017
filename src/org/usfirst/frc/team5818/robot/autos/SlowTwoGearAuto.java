package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment.AutoExtra;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.PlaceGear;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.TwoGearSegment;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SlowTwoGearAuto extends CommandGroup {

    private TwoGearSegment moveForward;
    private TwoGearSegment moveToGear;
    private TwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;
    

    public SlowTwoGearAuto() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null,.5);

        gearMode = new GearMode();
        moveToGear = new TwoGearSegment(Direction.FORWARD, Side.LEFT, AutoExtra.COLLECT,.5);
        tapeMode2 = new TapeMode();
        moveToPeg = new TwoGearSegment(Direction.BACKWARD, Side.LEFT, AutoExtra.PLACE,.5);
        
        
        

        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new PlaceGear());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
    }

}