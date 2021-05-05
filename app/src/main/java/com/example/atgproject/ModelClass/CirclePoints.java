package com.example.atgproject.ModelClass;

public class CirclePoints {

    public static final String CIRCLE = "CIRCLE_TYPE";
    public static final String ARC = "ARC_TYPE";

    public enum TYPES {
        CIRCLE, ARC
    }

    float centerX;
    float centerY;
    float radius;
    float startAngle;
    float sweepedAngle;
    TYPES TYPE;

    public CirclePoints(float centerX, float centerY, float radius, float startAngle,
                        float sweepedAngle, TYPES type) {
        this.TYPE = type;
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.startAngle =startAngle;
        this.sweepedAngle = sweepedAngle;
    }

    public TYPES getTYPE() {
        return TYPE;
    }

    public void setTYPE(TYPES TYPE) {
        this.TYPE = TYPE;
    }

    public float getSweepedAngle() {
        return sweepedAngle;
    }

    public void setSweepedAngle(float sweepedAngle) {
        this.sweepedAngle = sweepedAngle;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getRadius() {
        return radius;
    }
}
