package com.stefanini.springboot.app.util.payment;

public class PSE extends PaymentMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con PSE fue éxito!", amount);
    }
}