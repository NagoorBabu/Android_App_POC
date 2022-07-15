package com.mfcwl.app.solidprinciples.solidprincipleinjava.singleresponsibilityprinciple.problem.classesproblem;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.List;

public class Client {
    private String clientId;
    private String name;
    private String lastName;

    public Client(String clientId, String name, String lastName) {
        this.clientId = clientId;
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Calculates the subtotal invoice.
     *
     * @param articles List of articles bought by the client.
     * @return Invoice subtotal.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BigDecimal calculateSubTotalInvoice(List<Article> articles) {
        return articles
                .stream()
                .map(article -> article.getUnitPrice()
                        .multiply(new BigDecimal(String.valueOf(article.getAmount()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    /**
     * Calculates the total invoice.
     *
     * @param subtotal      Invoice subtotal.
     * @param taxPercentage Tax percentage.
     * @return Total invoice.
     */
    public BigDecimal calculateTotalInvoice(BigDecimal subtotal, BigDecimal taxPercentage) {
        return subtotal.add(subtotal.multiply(taxPercentage));
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
