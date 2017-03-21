package org.usfirst.frc.team5818.robot.utils;

import org.usfirst.frc.team5818.robot.Robot;
import org.usfirst.frc.team5818.robot.constants.Side;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower {

    private double kp;
    private double kv;
    private double ka;
    private double current_heading = 0;
    private double startTime;
    private int current_segment;
    private Trajectory profile;
    private Side side;

    public TrajectoryFollower(double p, double v, double a, Trajectory prof, Side s) {
        side = s;
        kp = p;
        kv = v;
        ka = a;
        profile = prof;
    }

    public void reset() {
        current_segment = 0;
        startTime = Timer.getFPGATimestamp();
    }

    public double calculate(double distance_so_far) {
        double elapsed = Timer.getFPGATimestamp() - startTime;
        double fractionalSeg = elapsed/profile.getSegment(0).dt;
        if (current_segment < profile.getNumSegments()) {
            Trajectory.Segment segment = profile.getSegment(current_segment);
            double error = segment.pos - distance_so_far;
            double output = kp * error + kv * segment.vel + ka * segment.acc;
            //double output = kv * segment.vel;
            SmartDashboard.putNumber("target_vel", segment.vel + .1*(current_segment%2));
            SmartDashboard.putNumber("target_acc", segment.acc + .1*(current_segment%2));
            SmartDashboard.putNumber("target_pos", segment.pos + .1*(current_segment%2));
            if(side == Side.LEFT){
                SmartDashboard.putNumber("error_left", error);
                SmartDashboard.putNumber("output_left", output + .001*(current_segment%2));
            }else{
                SmartDashboard.putNumber("error_right", error);
                SmartDashboard.putNumber("output_right", output + .001*(current_segment%2));
            }
            current_heading = segment.heading;
            current_segment++;
            return output;
        } else {
            return 0;
        }
    }

    public double getHeading() {
        return current_heading;
    }

    public boolean isFinishedTrajectory() {
        return current_segment >= profile.getNumSegments();
    }

    public int getCurrentSegment() {
        return current_segment;
    }

    public int getNumSegments() {
        return profile.getNumSegments();
    }
}
