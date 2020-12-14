package com.stefanini.springboot.app.util;

public class EFE extends PaynetMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con efectivo fue éxito!", amount);
    }
}
