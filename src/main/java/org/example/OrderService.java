package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OrderService{
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
        long start = System.currentTimeMillis();
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()){

            var revenueFuture = CompletableFuture.supplyAsync(()
                    -> getActiveOrders(orders).stream()
                    .mapToDouble(Order::total)
                    .sum(), executorService);

            var countFuture = CompletableFuture.supplyAsync(()
                    -> getActiveOrders(orders).size(), executorService);

            return revenueFuture.thenCombine(countFuture, (revenue, count) -> {
                double avgCheck = revenue / count;
                long duration = System.currentTimeMillis() - start;

                return """
                        Отчёт
                        Выручка %.2f
                        Средний чек %.2f
                        Обработано заказов %d
                        Время генерации %d мс
                        """.formatted(revenue, avgCheck, count, duration);
            })
                    .join();
        }
    }
}
