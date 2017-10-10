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

/**
 * Factory class for creating Trajectories.
 *
 * @author jproney
 */
public class TrajectoryGenerator {

    /**
     * Generate a trajectory from a start state to a goal state. The starting
     * velocity (WARNING: May be ignored)
     * 
     * @param start_heading
     *            The starting heading
     * @param goal_pos
     *            The goal position
     * @param goal_vel
     *            The goal velocity (WARNING: May be ignored)
     * @param goal_heading
     *            The goal heading
     * @return A Trajectory that satisfies the relevant constraints and end
     *         conditions.
     */
    public static Trajectory generate(double max_vel, double max_acc, double dt, double start_vel, double start_heading,
            double goal_pos, double goal_vel, double goal_heading) {

        // How fast can we go given maximum acceleration and deceleration?
        double start_discount = .5 * start_vel * start_vel / max_acc;
        double end_discount = .5 * goal_vel * goal_vel / max_acc;

        double adjusted_max_vel = Math.min(max_vel, Math.sqrt(max_acc * goal_pos - start_discount - end_discount));
        double t_rampup = (adjusted_max_vel - start_vel) / max_acc;
        double x_rampup = start_vel * t_rampup + .5 * max_acc * t_rampup * t_rampup;
        double t_rampdown = (adjusted_max_vel - goal_vel) / max_acc;
        double x_rampdown = adjusted_max_vel * t_rampdown - .5 * max_acc * t_rampdown * t_rampdown;
        double x_cruise = goal_pos - x_rampdown - x_rampup;
        double t_cruise = x_cruise / adjusted_max_vel;

        // The +.5 is to round to nearest
        int segCount = (int) ((t_rampup + t_rampdown + t_cruise) / dt + .5);

        Trajectory traj = new Trajectory(segCount);
        Trajectory.Segment first = new Trajectory.Segment();
        first.pos = 0;
        first.vel = start_vel;
        first.acc = 0;
        first.dt = dt;
        Trajectory.Segment last = new Trajectory.Segment();
        last.pos = goal_pos;
        last.vel = goal_vel;
        last.acc = 0;
        last.dt = dt;

        traj.setSegment(0, first);
        traj.setSegment(traj.getNumSegments() - 1, last);
        double total_heading_change = goal_heading - start_heading;
        for (int i = 1; i < traj.getNumSegments() - 1; i++) {
            double currTime = i * dt;
            if (currTime <= t_rampup) {
                traj.segments[i].acc = max_acc;
                traj.segments[i].vel = max_acc * currTime;
                traj.segments[i].pos = .5 * max_acc * currTime * currTime;
            } else if (currTime <= t_rampup + t_cruise) {
                traj.segments[i].acc = 0;
                traj.segments[i].vel = adjusted_max_vel;
                traj.segments[i].pos = x_rampup + adjusted_max_vel * (currTime - t_rampup);
            } else {
                double decelTime = currTime - t_cruise - t_rampup;
                traj.segments[i].acc = -max_acc;
                traj.segments[i].vel = adjusted_max_vel - max_acc * decelTime;
                traj.segments[i].pos =
                        x_rampup + x_cruise + adjusted_max_vel * decelTime - .5 * max_acc * decelTime * decelTime;
            }
            traj.segments[i].heading = start_heading
                    + total_heading_change * (traj.segments[i].pos) / traj.segments[traj.getNumSegments() - 1].pos;
            traj.segments[i].dt = dt;
        }

        return traj;
    }

}