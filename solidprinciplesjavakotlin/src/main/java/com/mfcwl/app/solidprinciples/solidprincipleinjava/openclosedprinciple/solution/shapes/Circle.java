package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.interfacedetails.GeometricFigure;

public class Circle implements GeometricFigure {

    public static final double PI = 3.1416;
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return (Circle.PI * (this.radius * this.radius));
    }
}
