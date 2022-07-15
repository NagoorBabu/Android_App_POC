package com.mfcwl.app.solidprinciples.solidprincipleinjava.singleresponsibilityprinciple.solution;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mfcwl.app.solidprinciples.solidprincipleinjava.singleresponsibilityprinciple.problem.classesproblem.Article;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceCalculator {

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

}
