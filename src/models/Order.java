package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Order {

    private int orderId;

    private Customer customer;

    private LocalDate orderDate;

    private double totalAmount;

    private List<OrderDetail> orderDetails;

    public Order(int orderId, Customer customer, LocalDate orderDate, double totalAmount, List<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderDetails = orderDetails;
    }

    public Order(int orderId, LocalDate orderDate, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderId: " + orderId +
                ", OrderDate: " + orderDate +
                ", TotalAmount: " + totalAmount;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

