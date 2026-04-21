package org.example;

import java.util.ArrayList;
import java.util.List;

//TIP Чтобы <b>запустить</b> код, нажмите <shortcut actionId="Run"/> или
// нажмите на значок <icon src="AllIcons.Actions.Execute"/> на полях.
public class Main {
    static void main() {

        List<Order> orders = new ArrayList<>();
        orders.add(new Order("0", "Иванов", "Масло", 120, 2, Order.Status.PAID));
        orders.add(new Order("0", "Иванов", "Масло", 320, 1, Order.Status.PAID));
        OrderService orderService = new OrderService();
        System.out.println(orderService.generateReport(orders));
    }
}
