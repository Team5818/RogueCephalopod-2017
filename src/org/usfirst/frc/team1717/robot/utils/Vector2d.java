package org.usfirst.frc.team1717.robot.utils;

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
    
    public Vector2d scale(double x, double y){
    	return new Vector2d(getX()*x, getY()*y);
    }
    
    public Vector2d scale(double scale){
    	return scale(scale,scale);
    }
    // TODO: more vector math!

}
