package com.stefanini.springboot.app.util.payment;

public abstract class PaymentMethod {
    public abstract String payBill(long amount);
}
