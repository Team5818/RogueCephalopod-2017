package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicDrive;
import org.usfirst.frc.team5818.robot.commands.MagicSpin;
import org.usfirst.frc.team5818.robot.commands.MagicSpinToVision;
import org.usfirst.frc.team5818.robot.commands.ShiftGears;
import org.usfirst.frc.team5818.robot.commands.TapeMode;
import org.usfirst.frc.team5818.robot.commands.driveatratio.DriveAtRatio;
import org.usfirst.frc.team5818.robot.commands.placewithlimit.PlaceWithLimit;
import org.usfirst.frc.team5818.robot.constants.Camera;
import org.usfirst.frc.team5818.robot.constants.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MagicSideGear extends CommandGroup{
    
    private Position position;
    private double firstDistance;
    private double turnAngle;
    private double secondDistance;
    
    public enum Position{
        BLUE_BOILER, BLUE_OPPOSITE, RED_BOILER, RED_OPPOSITE
    }
    
    public MagicSideGear(Position pos) {
        position = pos;
        switch(position) {
            case BLUE_BOILER:
                firstDistance = -69.6;
                turnAngle = Math.toRadians(60);
                secondDistance = -68;
                break;
            case BLUE_OPPOSITE:
                firstDistance = -70.4;
                turnAngle = Math.toRadians(-60);
                secondDistance = -67;
                break;
            case RED_BOILER:
                firstDistance = -69.6;
                turnAngle = Math.toRadians(-60);
                secondDistance = -68;
                break;
            case RED_OPPOSITE:
                firstDistance = -70.4;
                turnAngle = Math.toRadians(60);
                secondDistance = -67;
                break;
        }
        
        this.addSequential(new MagicDrive(firstDistance, 400));
        this.addSequential(new MagicSpin(turnAngle));
        this.addSequential(new TapeMode());
        //this.addSequential(new MagicSpinToVision());
        this.addSequential(new ShiftGears(Gear.LOW));
        addSequential(DriveAtRatio.withVision(Camera.CAM_TAPE, b -> {
            b.inches(Math.abs(secondDistance) - 5);
            b.maxPower(.7);
            b.maxRatio(3.0);
            b.stoppingAtEnd(true);
        }));
        addSequential(DriveAtRatio.withDeadReckon(b -> {
            b.inches(5);
            b.maxPower(.7);
            b.targetRatio(1);
            b.stoppingAtEnd(true);
        }));
        this.addSequential(new PlaceWithLimit());
        this.addSequential(new ShiftGears(Gear.HIGH));
        this.addSequential(new MagicDrive(-secondDistance));
        this.addSequential(new MagicSpin(Math.PI));
        this.addSequential(new MagicDrive(320.0));
    }
    
}
