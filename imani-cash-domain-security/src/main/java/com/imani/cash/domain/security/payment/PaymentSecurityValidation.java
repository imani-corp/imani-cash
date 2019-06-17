package com.imani.cash.domain.security.payment;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author manyce400
 */
public class PaymentSecurityValidation {


    private final String message;

    public static final PaymentSecurityValidation NONE = new PaymentSecurityValidation();

    public PaymentSecurityValidation() {
        this.message = null;
    }

    public PaymentSecurityValidation(String message) {
        Assert.notNull(message, "message cannot be null");
        this.message = message;
    }

    public Optional<String> getMessage() {
        return message == null ? Optional.empty() : Optional.of(message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .toString();
    }

    public static PaymentSecurityValidation of(String message) {
        return new PaymentSecurityValidation(message);
    }
}
