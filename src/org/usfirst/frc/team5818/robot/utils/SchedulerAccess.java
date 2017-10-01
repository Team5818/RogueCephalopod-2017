/*
 * This file is part of Rogue-Cephalopod, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
