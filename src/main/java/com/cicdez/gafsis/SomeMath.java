package com.cicdez.gafsis;

public class SomeMath {
    public static double clamp(double v, double min, double max) {
        return v <= min ? min : (v >= max ? max : v);
    }
    public static int clamp(int v, int min, int max) {
        return v <= min ? min : (v >= max ? max : v);
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return getDistance(x2 - x1, y2 - y1);
    }
    public static double getDistance(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}
