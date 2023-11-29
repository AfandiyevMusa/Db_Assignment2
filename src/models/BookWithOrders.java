package models;

import java.util.List;

public class BookWithOrders {
    private Book book;
    private List<Order> orders;

    public BookWithOrders(Book book, List<Order> orders) {
        this.book = book;
        this.orders = orders;
    }

    public Book getBook() {
        return book;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
