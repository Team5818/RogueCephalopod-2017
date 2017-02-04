package org.usfirst.frc.team5818.robot.utils;

/**
 * Created by Yoseph Alabdulwahab on 3/29/2016.
 * This is a lookup table class. It can be used
 * to simulate functions based on known data
 * points
 */
public class LinearLookupTable {

    private final double[] xArr;
    private final double[] yArr;

    /**
     * Initializes a linear interpolation lookup table.
     *
     * @param x the array of x values for this table. Must be in order.
     * @param y the array of y values for this table. Must be in order.
     */
    public LinearLookupTable(double[] x, double[] y) {
        if (x.length != y.length || x.length == 0)
            throw new RuntimeException("Could not create a lookup table with different number of x and y values.");
        this.xArr = x;
        this.yArr = y;
    }

    /**
     * @param x the x value that is being searched for.
     * @return the linearly interpolated y value based on the entries.
     */
    public double getEstimate(double x) {
        int b = find(xArr, x);
        int a = b - 1;
        if(xArr.length == 1) {
            a = 0;
            b = 0;
        }
        else if (b <= 0) {
            b = 1;
            a = 0;
        }
        else if(b >= xArr.length - 1) {
            b = xArr.length - 1;
            a = b - 1;
        }
        double slope = (yArr[b] - yArr[a]) / (xArr[b] - xArr[a]);
        double y = slope * (x - xArr[a]) + yArr[a];
        return y;
    }

    /**
     * Gets the y value of which has an x value closest to the input.
     *
     * @param x the x value that is being searched for.
     * @return the y value of the entry whose x is closer to the input.
     */
    public double getClosestValue(double x) {
        int b = find(xArr, x);
        if (b <= 0 || b >= xArr.length - 1)
            return yArr[b];
        int a = b - 1;
        if (Math.abs(Math.abs(xArr[a]) - Math.abs(x)) > Math.abs(Math.abs(xArr[b]) - Math.abs(x)))
            return yArr[a];
        return yArr[b];
    }

    /**
     * Returns the location of that element, or the location of the next biggest element.
     *
     * @param a   the specified array.
     * @param key the value desired to search for.
     * @return the index of the given value or the next biggest value.
     */
    private int find(double[] a, double key) {
        int imin = 0, imax = a.length - 1;
        int imid = imin + (imax - imin) / 2;
        while (imin <= imax) {
            imid = imin + (imax - imin) / 2;
            if (a[imid] == key)
                return imid;
            else if (a[imid] < key)
                imin = imid + 1;
            else
                imax = imid - 1;
        }
        if(imin > a.length - 1)
            imin = a.length - 1;
        else if(imin < 0)
            imin = 0;

        return imin;
    }
}
