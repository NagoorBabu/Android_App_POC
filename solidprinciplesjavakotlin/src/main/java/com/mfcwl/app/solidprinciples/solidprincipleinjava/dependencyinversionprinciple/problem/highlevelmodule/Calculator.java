package com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem.highlevelmodule;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem.lowlevelmodule.AddOperation;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem.lowlevelmodule.DivideOperation;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem.lowlevelmodule.MultiplyOperation;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem.lowlevelmodule.SubtractOperation;

public class Calculator {

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVISION
    }

    public double calculate(double a, double b, Operation operation) {

        double result = 0;

        switch (operation) {
            case ADD:
                AddOperation addOperation = new AddOperation();
                result = addOperation.add(a, b);
                break;

            case SUBTRACT:
                SubtractOperation subtractOperation = new SubtractOperation();
                result = subtractOperation.subtract(a, b);
                break;

            case MULTIPLY:
                MultiplyOperation multiplyOperation = new MultiplyOperation();
                result = multiplyOperation.multiply(a, b);

                break;

            case DIVISION:
                DivideOperation divideOperation = new DivideOperation();
                result = divideOperation.divide(a, b);

                break;
        }
        return result;
    }
}
