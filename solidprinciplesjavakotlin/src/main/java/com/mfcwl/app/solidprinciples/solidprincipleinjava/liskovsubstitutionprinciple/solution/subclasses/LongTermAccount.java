package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.subclasses;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.superclass.BankAccount;

public class LongTermAccount extends BankAccount {
    private double balance;

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

}
