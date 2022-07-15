package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.interfacedetails.GeometricFigure;

public class Rectangle implements GeometricFigure {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return this.width * this.height;
    }
}
