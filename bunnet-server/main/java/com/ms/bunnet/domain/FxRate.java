package com.ms.bunnet.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FxRate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String currencyName;
    private String shortCode;
    private BigDecimal rate;
    private char operator;

    public FxRate(String name, String code, BigDecimal rate, char operator) {
        this.currencyName = name;
        this.shortCode = code;
        this.operator = operator;
        this.rate = rate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "FxRate{" +
                "currencyName='" + currencyName + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", rate=" + rate +
                ", operator=" + operator +
                '}';
    }
}
