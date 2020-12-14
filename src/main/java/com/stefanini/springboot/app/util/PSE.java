package com.stefanini.springboot.app.util;

public class PSE extends PaynetMethod {

    @Override
    public String payBill(long amount) {
        return String.format("El pago de %d con PSE fue éxito!", amount);
    }
}
