package com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.app;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.areacalculator.AreaCalculator;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes.Circle;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes.Rectangle;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.openclosedprinciple.solution.shapes.Square;

public class App {

    public static void main(String[] args) {
        AreaCalculator areaCalculator = new AreaCalculator();

        Square square = new Square(3.15);
        Rectangle rectangle = new Rectangle(7.85, 10.85);
        Circle circle = new Circle(7.98);

        areaCalculator.calculateArea(square);
        areaCalculator.calculateArea(rectangle);
        areaCalculator.calculateArea(circle);
    }
}
