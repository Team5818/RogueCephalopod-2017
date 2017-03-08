package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.AutoSegment;
import org.usfirst.frc.team5818.robot.commands.GearMode;
import org.usfirst.frc.team5818.robot.commands.PlaceGear;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.constants.AutoExtra;
import org.usfirst.frc.team5818.robot.constants.Direction;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ThreeGearAuto extends CommandGroup {

    private AutoSegment moveForward;
    private AutoSegment moveToGear;
    private AutoSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;
    
    private GearMode gearMode2;
    private AutoSegment moveToGear2;
    private TapeMode tapeMode3;
    private AutoSegment moveToPeg2;
    

    public ThreeGearAuto() {
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new AutoSegment(Direction.BACKWARD, Side.CENTER, null,.7);

        gearMode = new GearMode();
        moveToGear = new AutoSegment(Direction.FORWARD, Side.LEFT, AutoExtra.COLLECT,.7);
        tapeMode2 = new TapeMode();
        moveToPeg = new AutoSegment(Direction.BACKWARD, Side.LEFT, AutoExtra.PLACE,.7);
        
        gearMode2 = new GearMode();
        moveToGear2 = new AutoSegment(Direction.FORWARD, Side.RIGHT, AutoExtra.COLLECT,.7);
        tapeMode3 = new TapeMode();
        moveToPeg2 = new AutoSegment(Direction.BACKWARD, Side.RIGHT, AutoExtra.PLACE,.7);
        
        

        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
        this.addSequential(new PlaceGear());
        this.addSequential(gearMode);
        this.addSequential(moveToGear);
        this.addSequential(tapeMode2);
        this.addSequential(moveToPeg);
        this.addSequential(gearMode2);
        this.addSequential(moveToGear2);
        this.addSequential(tapeMode3);
        this.addSequential(moveToPeg2);
    }

}
