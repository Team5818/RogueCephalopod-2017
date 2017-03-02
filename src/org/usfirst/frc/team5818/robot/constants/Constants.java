package org.usfirst.frc.team5818.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public abstract class Constants {

    private static final String NO_ROBOT = "No robot-id preference configured!";
    public static final Constants Constant;
    static {
        String robotId = Preferences.getInstance().getString("robot-id", NO_ROBOT);
        switch (robotId) {
            case "kitbot":
                Constant = new KitbotConstants();
                break;
            case "arronax":
                Constant = new ArronaxConstants();
                break;
            default:
                throw new IllegalStateException(NO_ROBOT);
        }
    }

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
