package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.areacalculator;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.interfacedetails.GeometricFigure;

public class AreaCalculator {

    public double calculateArea(GeometricFigure figure) {
        return figure.calculateArea();
    }
}
