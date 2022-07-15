package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.problem.areacalculator;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.problem.rectangle.Rectangle;

public class AreaCalculator {

    public double calculateArea(Rectangle rectangle) {
        return rectangle.getWidth() * rectangle.getHeight();
    }
}
