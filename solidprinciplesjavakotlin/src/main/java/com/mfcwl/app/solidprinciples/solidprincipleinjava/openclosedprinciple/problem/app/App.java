package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.problem.app;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.problem.areacalculator.AreaCalculator;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.problem.rectangle.Rectangle;

public class App {

    public static void main(String[] args) {

        AreaCalculator areaCalculator = new AreaCalculator();

        Rectangle rect1 = new Rectangle(13.5, 14);
        Rectangle rect2 = new Rectangle(7.89, 9.85);

        areaCalculator.calculateArea(rect1);
        areaCalculator.calculateArea(rect2);
    }
}
