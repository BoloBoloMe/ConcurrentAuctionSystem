package com.bolo.auction.business.repository.entity;

import java.math.BigDecimal;

public class AccountAmount {
    private Long accountId;
    private BigDecimal amount;
    private BigDecimal freeze;

    protected AccountAmount() {
    }

    public AccountAmount(Long accountId, BigDecimal amount, BigDecimal freeze) {
        this.accountId = accountId;
        this.amount = amount;
        this.freeze = freeze;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }
}
