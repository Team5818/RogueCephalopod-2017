package org.usfirst.frc.team5818.robot.utils;

/**
 * Implementation of a Trajectory using arrays as the underlying storage
 * mechanism.
 *
 * @author Jared341
 * @author jproney
 */
public class Trajectory {

    public static class Pair {

        public Pair(Trajectory left, Trajectory right) {
            this.left = left;
            this.right = right;
        }

        public Trajectory left;
        public Trajectory right;
    }

    public static class Segment {

        public double pos, vel, acc, heading, dt, x, y;

        public Segment() {
        }

        public Segment(double pos, double vel, double acc, double heading, double dt, double x, double y) {
            this.pos = pos;
            this.vel = vel;
            this.acc = acc;
            this.heading = heading;
            this.dt = dt;
            this.x = x;
            this.y = y;
        }

        public Segment(Segment to_copy) {
            pos = to_copy.pos;
            vel = to_copy.vel;
            acc = to_copy.acc;
            heading = to_copy.heading;
            dt = to_copy.dt;
            x = to_copy.x;
            y = to_copy.y;
        }

        public String toString() {
            return "pos: " + pos + "; vel: " + vel + "; acc: " + acc + "; heading: " + heading;
        }
    }

    Segment[] segments = null;
    boolean inverted_y = false;

    public Trajectory(int length) {
        segments = new Segment[length];
        for (int i = 0; i < length; i++) {
            segments[i] = new Segment();
        }
    }

    public Trajectory(Segment[] segs) {
        segments = segs;
    }

    public void setInvertedY(boolean inverted) {
        inverted_y = inverted;
    }

    public int getNumSegments() {
        return segments.length;
    }

    public Segment getSegment(int index) {
        if (index < getNumSegments()) {
            if (!inverted_y) {
                return segments[index];
            } else {
                Segment segment = new Segment(segments[index]);
                segment.y *= -1.0;
                segment.heading = MathUtil.wrapAngleRad(2 * Math.PI - segment.heading);
                return segment;
            }
        } else {
            return new Segment();
        }
    }

    public Segment interpolateSegment(double index) {
        double frac = index - (int) index;
        Segment before = getSegment((int) index);
        Segment after = getSegment((int) (index + .5));
        Segment middle = new Segment();
        middle.acc = before.acc + (after.acc - before.acc) * frac;
        middle.vel = before.vel + (after.vel - before.vel) * frac;
        middle.pos = before.pos + (after.pos - before.pos) * frac;
        middle.heading = before.heading + (after.heading - before.heading) * frac;
        middle.dt = before.dt;
        return middle;
    }

    public void setSegment(int index, Segment segment) {
        if (index < getNumSegments()) {
            segments[index] = segment;
        }
    }

    public void scale(double scaling_factor) {
        for (int i = 0; i < getNumSegments(); i++) {
            segments[i].pos *= scaling_factor;
            segments[i].vel *= scaling_factor;
            segments[i].acc *= scaling_factor;
        }
    }

    public void append(Trajectory to_append) {
        Segment[] temp = new Segment[getNumSegments() + to_append.getNumSegments()];

        for (int i = 0; i < getNumSegments(); i++) {
            temp[i] = new Segment(segments[i]);
        }
        for (int i = 0; i < to_append.getNumSegments(); i++) {
            temp[i + getNumSegments()] = new Segment(to_append.getSegment(i));
        }

        this.segments = temp;
    }

    public Trajectory copy() {
        Trajectory cloned = new Trajectory(getNumSegments());
        cloned.segments = copySegments(this.segments);
        return cloned;
    }

    private Segment[] copySegments(Segment[] tocopy) {
        Segment[] copied = new Segment[tocopy.length];
        for (int i = 0; i < tocopy.length; i++) {
            copied[i] = new Segment(tocopy[i]);
        }
        return copied;
    }

    public String toString() {
        String str = "Segment\tPos\tVel\tAcc\tHeading\tAngVel\n";
        for (int i = 0; i < getNumSegments(); i++) {
            Trajectory.Segment segment = getSegment(i);
            str += i + "\t";
            str += segment.pos + "\t";
            str += segment.vel + "\t";
            str += segment.acc + "\t";
            str += segment.heading + "\t";
            str += "\n";
        }

        return str;
    }

    public String toStringProfile() {
        return toString();
    }

    public String toStringEuclidean() {
        String str = "Segment\tx\ty\tHeading\n";
        for (int i = 0; i < getNumSegments(); i++) {
            Trajectory.Segment segment = getSegment(i);
            str += i + "\t";
            str += segment.x + "\t";
            str += segment.y + "\t";
            str += segment.heading + "\t";
            str += "\n";
        }

        return str;
    }
}