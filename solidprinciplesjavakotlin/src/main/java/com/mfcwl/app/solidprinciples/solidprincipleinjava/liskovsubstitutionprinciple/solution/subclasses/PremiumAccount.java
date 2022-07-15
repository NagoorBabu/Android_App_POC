package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.subclasses;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.superclass.WithdrawableAccount;

public class PremiumAccount extends WithdrawableAccount {
    private double balance;
    private int preferencePoints;

    @Override
    public void deposit(double amount) {
        balance += amount;
        accumulatePreferencePoints();
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance < amount)
            return false;
        else {
            balance -= amount;
            accumulatePreferencePoints();
            return true;
        }
    }

    private void accumulatePreferencePoints() {
        preferencePoints++;
    }
}
