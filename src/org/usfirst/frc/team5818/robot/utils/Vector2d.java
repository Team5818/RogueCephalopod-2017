package org.usfirst.frc.team5818.robot.utils;

/**
 * A simple 2D vector class.
 */
public class Vector2d {

    private final double x;
    private final double y;

    /**
     * Creates a new vector from an x and y.
     * 
     * @param x
     *            - x value
     * @param y
     *            - y value
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value.
     * 
     * @return The x value.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y value.
     * 
     * @return The y value
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector2d)) {
            return false;
        }
        Vector2d other = (Vector2d) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    public Vector2d scale(double x, double y) {
        return new Vector2d(getX() * x, getY() * y);
    }

    public Vector2d scale(double scale) {
        return scale(scale, scale);
    }

    public Vector2d normalize() {
        return normalize(1.0);
    }

    public Vector2d normalize(double target) {
        return new Vector2d(x / Math.max(x, y) * target, y / Math.max(x, y) * target);
    }

    public double componentRatio() {
        return x / y;
    }

    public double average() {
        return (x + y) / 2;
    }

    public Vector2d abs() {
        return new Vector2d(Math.abs(x), Math.abs(y));
    }

    // TODO: more vector math!

}
