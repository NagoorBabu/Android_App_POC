package com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.solution.lowlevelmodule;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.solution.abstraction.CalculatorOperation;

public class DivideOperation implements CalculatorOperation {

    @Override
    public double calculate(double a, double b) {
        return a / b;
    }
}
