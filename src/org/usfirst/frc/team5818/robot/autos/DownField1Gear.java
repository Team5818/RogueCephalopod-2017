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

public class DownField1Gear extends CommandGroup {

    private TwoGearSegment moveForward;
    private TwoGearSegment moveToGear;
    private TwoGearSegment moveToPeg;
    private TapeMode tapeMode1;
    private GearMode gearMode;
    private TapeMode tapeMode2;

    public DownField1Gear(Side side) {
        double angMult;
        double sideDist;
        double waitTime;
        ///FOR BLUE SIDE
        if(side == Side.LEFT){
            angMult = 1;
            sideDist = 80;
            waitTime = 0;
        }
        else{
            angMult = -1;
            sideDist = 120;
            waitTime = 3;
        }
        setInterruptible(false);
        tapeMode1 = new TapeMode();
        moveForward = new TwoGearSegment(Direction.BACKWARD, Side.CENTER, null, -.9);

        gearMode = new GearMode();
        moveToGear = new TwoGearSegment(Direction.FORWARD, Side.LEFT, AutoExtra.COLLECT, -.75);
        tapeMode2 = new TapeMode();
        moveToPeg = new TwoGearSegment(Direction.BACKWARD, Side.LEFT, AutoExtra.PLACE, -.9);

        this.addSequential(new ShiftGears(Gear.LOW, .2));
        this.addSequential(tapeMode1);
        this.addSequential(moveForward);
//        this.addSequential(new TimedCommand(0.2));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new PlaceWithLimit());
//        this.addSequential(gearMode);
//        this.addSequential(moveToGear);
//        this.addSequential(tapeMode2);
//        this.addSequential(moveToPeg);
        // this.addSequential(new TimedCommand(0.5));
//        this.addSequential(new PlaceWithLimit());
        //this.addSequential(new PlaceWithLimit());
        this.addSequential(new DriveTrajectory(40, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        //this.addSequential(new TimedCommand(waitTime));
        this.addSequential(new SpinWithProfile(angMult*Math.toRadians(80),true, true));
        this.addSequential(new DriveTrajectory(sideDist, 0.0, 0.0, 0.0, Direction.FORWARD, true));
        this.addSequential(new SpinWithProfile(angMult*Math.PI, true, true));
        this.addSequential(new DriveTrajectory(370, angMult*Math.PI, 0.0, 0.0, Direction.FORWARD, true));


    }

}
