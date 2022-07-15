package com.mfcwl.app.solidprinciples.solidprincipleinjava.dependencyinversionprinciple.problem;

public class Comments {
    /*Dependency Inversion Principle - https://dev.to/victorpinzon1988eng/solid-dependency-inversion-principle-5f8m*/

    /*Calculator is High Level Module and Operations Add, Subtract, Multiply and Divide are low level modules*/
    /*High Level Module 'Calculator' depends on Low Level Modules 'AddOperation', 'SubtractOperation', 'MultiplyOperation' and 'DivideOperation'*/
    /*Calculator has tight coupling with the lower modules, We can't modify any operation modules without modifying calculator module and
    * If we want to add new operation 'SquareOperation', we need to modify Calculator Module which violates Open/Closed Principle*/
}
