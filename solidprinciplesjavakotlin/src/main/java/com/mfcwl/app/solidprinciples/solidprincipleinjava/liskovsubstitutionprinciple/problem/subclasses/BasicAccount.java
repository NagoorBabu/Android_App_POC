package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.subclasses;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.superclass.BankAccount;

public class BasicAccount extends BankAccount {
    private double balance;

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance < amount)
            return false;
        else {
            balance -= amount;
            return true;
        }
    }
}
