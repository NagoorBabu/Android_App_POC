package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem;

public class Comments {
    /*https://dev.to/victorpinzon1988eng/solid-liskov-substitution-principle-3jel*/

    /*The LSP principle says that objects of a superclass should be replaceable with objects of its subclasses
     * without breaking the application
     * BankAccount objects wouldn't be completely interchangeable with LongTermAccount objects because if
     * we try to execute the withdrawal method we would get an exception. As a solution for this issue,
     * we can condition the debitAdministrativeExpenses method, so we can skip the LongTermAccount objects
     * but this would violate the Open/Closed Principle*/
}
