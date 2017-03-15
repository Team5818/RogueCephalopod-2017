package org.usfirst.frc.team5818.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public abstract class Constants {

    private static final String COMP_BOT = "rogue-cephalopod";
    public static final Constants Constant;
    static {
        String robotId = Preferences.getInstance().getString("robot-id", COMP_BOT);
        switch (robotId) {
            case "kitbot":
                Constant = new KitbotConstants();
                break;
            case "rogue-cephalopod":
                Constant = new RogueCephalopod();
                break;
            default:
                throw new IllegalStateException("Illegal robot ID!!");
        }
    }

    public abstract double turretScale();

    public abstract double turretCenter();

    public abstract double encoderScale();

    public abstract double wheelToWheelWidth();

    public final double maxPower() {
        return 1.0;
    }

    public final int joystickForwardBack() {
        return 0;
    }

    public final int joystickTurn() {
        return 1;
    }

    public final int joystickTurret() {
        return 2;
    }

    public final int joystickCollector() {
        return 3;
    }

    public final double maxVelocity() {
        return 500;
    }

    public final int turretPot() {
        return 0;
    }

    public final double cameraFov() {
        return 60;
    }

    public final double cameraWidth() {
        return 160;
    }

}
