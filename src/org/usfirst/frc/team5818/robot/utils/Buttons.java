package org.usfirst.frc.team5818.robot.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Buttons {

    private static final boolean USING_SIX_JOYSTICKS = true;

    public static final ButtonContainer FW_BACK = new ButtonContainer(0);
    public static final ButtonContainer TURN = new ButtonContainer(1);
    public static final ButtonContainer TURRET = new ButtonContainer(2);
    public static final ButtonContainer COLLECTOR = new ButtonContainer(3);

    public static final class ButtonContainer {

        private final int joystick;

        private ButtonContainer(int joystick) {
            this.joystick = joystick;
        }

        public Button get(int button) {
            return buttonMap.get(joystick).apply(button);
        }
    }

    private static final List<IntFunction<Button>> buttonMap = Arrays.asList(null, null, null, null);
    private static final Joystick[] joysticks = IntStream.range(0, 6).mapToObj(Joystick::new).toArray(Joystick[]::new);

    public static void setButtonMapMode() {
        if (USING_SIX_JOYSTICKS) {
            mapSixJoystick();
        } else {
            mapFourJoystick();
        }
    }

    private static void mapFourJoystick() {
        // map all buttons to themselves, pretty simple.
        for (int i = 0; i < 4; i++) {
            Joystick stick = joysticks[i];
            buttonMap.set(i, b -> new JoystickButton(stick, b));
        }
    }

    private static void mapSixJoystick() {
        // more complicated!
        remapJoystickSix(0, 4, 4);
        remapJoystickSix(1, 4, -2);
        remapJoystickSix(2, 5, 4);
        remapJoystickSix(3, 5, -2);
    }

    private static void remapJoystickSix(int virtual, int realDelegate, int offset) {
        Joystick realVirtual = joysticks[virtual];
        Joystick delegate = joysticks[realDelegate];
        buttonMap.set(virtual, i -> {
            switch (i) {
                case 1:
                case 2:
                    // 1 & 2 go on the real joystick with virtual id
                    return new JoystickButton(realVirtual, i);
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    // 3 - 8 map to delegate
                    return new JoystickButton(delegate, i + offset);
                default:
                    throw new IllegalArgumentException("Joystick " + virtual + " has no button " + i);
            }
        });
    }

    private Buttons() {
    }

}
