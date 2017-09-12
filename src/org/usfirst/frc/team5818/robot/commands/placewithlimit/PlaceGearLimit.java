package org.usfirst.frc.team5818.robot.commands.placewithlimit;

import org.usfirst.frc.team5818.robot.commands.SetExtendTurret;
import org.usfirst.frc.team5818.robot.commands.SetPunchTurret;
import org.usfirst.frc.team5818.robot.commands.SetTurretAngle;
import org.usfirst.frc.team5818.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PlaceGearLimit extends CommandGroup {

    public PlaceGearLimit() {
        SmartDashboard.putString("PGLStart", "not yet");
        SmartDashboard.putString("PGLEnd", "not yet");
        this.addSequential(new InstantCommand() {

            @Override
            protected void initialize() {
                SmartDashboard.putString("PGLStart", "started");
            }
        });
        this.addSequential(new SetExtendTurret(true));
        this.addSequential(new SetPunchTurret(true, .6));
        this.addSequential(new SetExtendTurret(false));
        this.addSequential(new SetPunchTurret(false, .01));
        this.addSequential(new InstantCommand() {

            @Override
            protected void initialize() {
                SmartDashboard.putString("PGLEnd", "ended");
            }
        });
        addSequential(new SetTurretAngle(Turret.TURRET_CENTER_POS));

    }

}
