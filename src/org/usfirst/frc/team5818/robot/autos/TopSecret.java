package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.DriveTrajectory;
import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;
import org.usfirst.frc.team5818.robot.commands.SetPunchTurret;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.constants.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class TopSecret extends CommandGroup{
    
    CommandGroup firstSeg;
    CommandGroup turretStuff;
    
    public TopSecret(){
        
        firstSeg = new CommandGroup();
        turretStuff = new CommandGroup();
        turretStuff.addSequential(new SetTurretAngle(60));
        turretStuff.addSequential(new SetExtendTurret(true));
        turretStuff.addSequential(new SetPunchTurret(true,0));
        turretStuff.addSequential(new TimedCommand(.3));
        turretStuff.addSequential(new SetExtendTurret(false));
        turretStuff.addSequential(new SetPunchTurret(false,0));
        firstSeg.addParallel(new DriveTrajectory(576, 0.0, 0.0, 0.0, Direction.BACKWARD, true));
        firstSeg.addParallel(turretStuff);
        this.addSequential(firstSeg);
        this.addSequential(new SetExtendTurret(true));
        this.addSequential(new SetPunchTurret(true,0));
        this.addSequential(new TimedCommand(.3));
        this.addSequential(new SetExtendTurret(false));
        this.addSequential(new SetPunchTurret(false,0));
    }
}
