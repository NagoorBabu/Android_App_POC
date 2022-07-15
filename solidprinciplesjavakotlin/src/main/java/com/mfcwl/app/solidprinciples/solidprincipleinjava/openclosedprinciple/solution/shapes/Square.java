package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.interfacedetails.GeometricFigure;

public class Square implements GeometricFigure {

    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return this.side * this.side;
    }
}
