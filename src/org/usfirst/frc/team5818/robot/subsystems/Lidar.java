package org.usfirst.frc.team5818.robot.subsystems;

import java.util.List;

import org.usfirst.frc.team5818.robot.commands.SweepReader;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import io.scanse.sweep.SweepDevice;
import io.scanse.sweep.SweepSample;

public class Lidar extends Subsystem {

    private final SweepDevice device =
            new SweepDevice(Preferences.getInstance().getString("sweep-dev", "/dev/ttyUSB0"));
    private Command sweepReader;
    private List<SweepSample> currentScan;

    public SweepDevice getDevice() {
        return device;
    }

    public void startSweeping() {
        if (!sweepReader.isRunning()) {
            sweepReader.start();
        }
    }

    public void stopSweeping() {
        if (sweepReader.isRunning()) {
            sweepReader.cancel();
        }
    }

    public void setCurrentScan(List<SweepSample> currentScan) {
        this.currentScan = currentScan;
    }

    public List<SweepSample> getCurrentScan() {
        return currentScan;
    }

    @Override
    protected void initDefaultCommand() {
        sweepReader = new SweepReader();
    }

}
