package org.usfirst.frc.team5818.robot.subsystems;

import static org.usfirst.frc.team5818.robot.constants.Constants.Constant;

import org.usfirst.frc.team5818.robot.RobotMap;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that manages vision dating coming from Raspberry Pi over Serial.
 * Constantly polls RPi in a separate thread, feeds info to other subsystems.
 * Also manages the LED ring :)
 */
public class VisionTracker extends Subsystem implements Runnable {

    /* Dank Meme Right Here */
    public static final int NO_VISION = 254;

    private SerialPort rasPi;
    private Port port;
    private volatile double currentAngle = 0.0;
    private String charBuffer = "";
    private Solenoid lightRing;

    public VisionTracker() {
        lightRing = new Solenoid(RobotMap.LED_SOLENOID);
        port = Port.kMXP;
        rasPi = new SerialPort(9600, port);
        rasPi.setReadBufferSize(1);
        rasPi.setTimeout(.1);
    }

    public SerialPort getRasPi() {
        return rasPi;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void read() {
        /* Repeatedly executes to get serial info from RPi */
        String output = "";
        try {
            output += (char) (rasPi.read(1)[0] & 0xFF);
        } catch (Exception e) {
            return;
        }
        // Look for newline delimiter
        if (output.equals("\n")) {
            // Make sure we have a full "packet"
            if (charBuffer.length() == 4) {
                int pixels = Integer.parseInt(charBuffer.substring(0, 4));// parse
                if (pixels == NO_VISION) {
                    // Lets us know we don't have a
                    // target
                    currentAngle = Double.NaN;
                } else {
                    // Use good-enough linear approximation to convert pixels ->
                    // angles
                    currentAngle = pixels * Constant.cameraFov() / Constant.cameraWidth() / 2.0;
                }
            }
            // reset if the "packet" was incomplete
            charBuffer = "";
        } else {
            // keep looking until full "packet" is received
            charBuffer += output;
        }
    }

    public void setLightsOn(boolean on) {
        lightRing.set(on);
    }

    @Override
    public void run() {
        while (true) {
            read();
        }

    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
