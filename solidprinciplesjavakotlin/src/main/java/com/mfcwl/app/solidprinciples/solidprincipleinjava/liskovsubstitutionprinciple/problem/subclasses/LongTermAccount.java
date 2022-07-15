package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.subclasses;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.superclass.BankAccount;

public class LongTermAccount extends BankAccount {
    private double balance;

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    /*Implementing Withdraw method is violation of LSP principle as LongTermAccount doesn't Withdraw normally.*/
    @Override
    public boolean withdraw(double amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
