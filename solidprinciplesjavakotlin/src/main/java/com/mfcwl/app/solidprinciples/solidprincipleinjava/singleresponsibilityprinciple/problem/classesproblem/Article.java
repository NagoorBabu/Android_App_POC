package com.mfcwl.app.solidprinciples.solidprincipleinjava.singleresponsibilityprinciple.problem.classesproblem;

import java.math.BigDecimal;

public class Article {

    private BigDecimal unitPrice;
    private BigDecimal amount;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
