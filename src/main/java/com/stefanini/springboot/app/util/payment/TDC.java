package com.stefanini.springboot.app.util.payment;

public class TDC extends PaymentMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con Tarjeta fue éxito!", amount);
    }
}
