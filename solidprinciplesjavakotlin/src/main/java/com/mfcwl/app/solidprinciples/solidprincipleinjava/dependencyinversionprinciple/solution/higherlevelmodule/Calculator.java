package com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.solution.higherlevelmodule;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.solution.abstraction.CalculatorOperation;

public class Calculator {

    public double calculate(double a, double b, CalculatorOperation operation) {
        return operation.calculate(a, b);
    }
}
