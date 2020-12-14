package com.stefanini.springboot.app.util;

public class TDC extends PaynetMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con Tarjeta fue éxito!", amount);
    }
}
