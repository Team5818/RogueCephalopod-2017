package org.usfirst.frc.team5818.robot.utils;

import java.lang.reflect.Field;
import java.util.Vector;

import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Utility to perform actions on {@link Scheduler}.
 */
public final class SchedulerAccess {

    private static final Field Scheduler_m_buttons = getField(Scheduler.class, "m_buttons");

    public static Vector<ButtonScheduler> getButtons(Scheduler scheduler) {
        return getValue(Scheduler_m_buttons, scheduler);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValue(Field f, Object instance) {
        try {
            return (T) f.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getField(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }

    }

    private SchedulerAccess() {
    }

}
