package org.example;

import java.time.LocalDate;

public record Order(
        String id,
        String customer,
        String product,
        double price,
        int quantity,
        Status status,
        LocalDate date
) {
    public Order {
        if (price <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Цена и количество должны быть > 0");
        }
    }

    public Order(String id, String customer, String product, double price, int quantity, Status status) {
        this(id, customer, product, price, quantity, status, LocalDate.now());
    }

    public double total() {
        return price * quantity;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status { PENDING, PAID, SHIPPED, CANCELLED }
}