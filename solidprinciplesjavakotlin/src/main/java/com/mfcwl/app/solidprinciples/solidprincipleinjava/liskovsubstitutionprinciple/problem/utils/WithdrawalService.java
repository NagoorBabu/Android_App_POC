package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.subclasses.BasicAccount;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.subclasses.LongTermAccount;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.subclasses.PremiumAccount;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.problem.superclass.BankAccount;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class WithdrawalService {
    public static final double ADMINISTRATIVE_EXPENSES_CHARGE = 25.00;

    public void cargarDebitarCuentas() {

        BankAccount basicAccount = new BasicAccount();
        basicAccount.deposit(100.00);

        BankAccount premiumAccount = new PremiumAccount();
        premiumAccount.deposit(200.00);

        BankAccount longTermAccount = new LongTermAccount();
        longTermAccount.deposit(400.00);

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(basicAccount);
        accounts.add(premiumAccount);
        accounts.add(longTermAccount);
        debitAdministrativeExpenses(accounts);
    }

    /*This violates open closed principle*/
    private void debitAdministrativeExpenses(List<BankAccount> accounts) {
        for (BankAccount account : accounts) {
            if (account instanceof LongTermAccount)
                continue;
            else
                account.withdraw(WithdrawalService.ADMINISTRATIVE_EXPENSES_CHARGE);
        }
    }
}
