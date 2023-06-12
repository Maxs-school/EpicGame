package org.amorgugus.Utils;

import org.amorgugus.Line;
import org.amorgugus.Point;
import org.amorgugus.Wall;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

public class MathUtils {
    /**
     * @param x1 The x of the first point
     * @param y1 The y of the first point
     * @param x2 the x of the second point
     * @param y2 the y of the second point
     * @return The angle in degrees that would point towards point 2 from point 1
     */
    public static double angleToPoint(double x1, double y1, double x2, double y2) {
        // Thanks Issac for suggestion atan2 instead of normal atan
        double angle = Math.toDegrees(Math.atan2((y2-y1),(x2-x1)));

        // Atan2 sometimes returns negative angles, this makes sure it is always positive, not technically required but makes the numbers look better
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }


    /**
     *
     * Returns the point that's some distance and angle from a location
     * @param angle
     * @param length
     * @param location
     * @return
     */
    public static Point pointAlongAngle(double angle, double length, Point location) {
        // Trig crap
        int xOffset = (int) (MathUtils.degreeCos(angle)*length);
        int yOffset = (int) (MathUtils.degreeSin(angle)*length);

        return new Point(location.getX()+xOffset, location.getY()+yOffset);
    }

    public static double degreeSin(double a) {
        return Math.sin(Math.toRadians(a));
    }

    public static double degreeCos(double a) {
        return Math.cos(Math.toRadians(a));
    }

    /**
     * Checks if a number is between two other numbers even if the two other numbers are in the wrong order
     * @param a The number you're checking
     * @param b Bound 1
     * @param c Bound 2
     * @return True if `a` is between the bounds
     */
    public static boolean isBetween(double a, double b, double c) {
        return (
                (c>=a && b<=a) ||
                (c<=a && b>=a)
                );
    }

    /**
     * Linearly interpolates between two points
     * @param bound1 The first bound
     * @param bound2 The second bound
     * @param percentage A value between 0 and 1 which represents how far between the bounds you are
     * @return the lerped result
     */
    public static double lerp(double bound1, double bound2, double percentage) {
        return (bound2 - bound1) * percentage;
    }

    public static double clamp(double a, double b, double c) {
        double[] arr = new double[] {
                a,b,c
        };
        return  Arrays.stream(arr).sorted().toArray()[1];
    }

    public static void getIntersections(Wall[] walls, Line playerLine, List<Point> intersections, Dictionary<Point, Wall> pointWallDictionary) {
        for (Wall wall :
                walls) {
            if (playerLine.doesIntersect(wall)) {
                Point intersect = playerLine.getIntersect(wall);
                intersections.add(intersect);
                pointWallDictionary.put(intersect, wall);
            }
        }
    }
}