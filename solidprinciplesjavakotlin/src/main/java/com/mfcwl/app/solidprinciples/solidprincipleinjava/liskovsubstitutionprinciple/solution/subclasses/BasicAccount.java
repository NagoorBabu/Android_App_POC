package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.subclasses;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.superclass.WithdrawableAccount;

public class BasicAccount extends WithdrawableAccount {
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
