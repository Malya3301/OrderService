package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    public List<Order> getActiveOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> (order.getStatus() == Order.Status.PAID) || (order.getStatus() == Order.Status.SHIPPED))
                .toList();
    }

    public Optional<Order> findById(List<Order> orders, String orderId) {
        return orders.stream()
                .filter(order -> orderId.equals(order.id()))
                .findFirst();
    }

    public Map<String, Double> getCustomerSpending(List<Order> orders) {
        return getActiveOrders(orders).stream()
                .collect(Collectors.groupingBy(Order::customer, Collectors.summingDouble(Order::price)));
    }

    public String generateReport(List<Order> orders) {

    }
}
