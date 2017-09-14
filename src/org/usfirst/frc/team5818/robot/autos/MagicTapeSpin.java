package org.usfirst.frc.team5818.robot.autos;

import org.usfirst.frc.team5818.robot.commands.MagicSpinToVision;
import org.usfirst.frc.team5818.robot.commands.TapeMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MagicTapeSpin extends CommandGroup{
    
    public MagicTapeSpin() {
        this.addSequential(new TapeMode());
        this.addSequential(new MagicSpinToVision(72.0));
    }
    
    
}
