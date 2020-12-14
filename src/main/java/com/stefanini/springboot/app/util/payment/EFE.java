package com.stefanini.springboot.app.util.payment;

public class EFE extends PaymentMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con efectivo fue éxito!", amount);
    }
}
