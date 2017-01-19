package utils;


/**
 * Calculators map raw power values (from joysticks or provided in auto) to
 * talon power.
 */
public interface DriveCalculator {

    /**
     * Converts raw power to talon power.
     * 
     * @param leftAndRight
     *            - raw power, as (x-axis, y-axis)
     * @return left and right talon power, as (left, right)
     */
    Vector2d compute(Vector2d leftAndRight);

}
