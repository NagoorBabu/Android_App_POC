package com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.utils;


import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.subclasses.BasicAccount;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.subclasses.PremiumAccount;
import com.mfcwl.app.solidprinciples.solidprincipleinjava.liskovsubstitutionprinciple.solution.superclass.WithdrawableAccount;

import java.util.ArrayList;
import java.util.List;

public class WithdrawalService {
    public static final double ADMINISTRATIVE_EXPENSES_CHARGE = 25.00;

    public void cargarDebitarCuentas() {

        WithdrawableAccount basicAccount = new BasicAccount();
        basicAccount.deposit(100.00);

        WithdrawableAccount premiumAccount = new PremiumAccount();
        premiumAccount.deposit(200.00);

        List<WithdrawableAccount> accounts = new ArrayList<>();
        accounts.add(basicAccount);
        accounts.add(premiumAccount);
        debitAdministrativeExpenses(accounts);
    }

    /*This violates open closed principle*/
    private void debitAdministrativeExpenses(List<WithdrawableAccount> accounts) {
        for (WithdrawableAccount account : accounts) {
            account.withdraw(WithdrawalService.ADMINISTRATIVE_EXPENSES_CHARGE);
        }
    }
}
