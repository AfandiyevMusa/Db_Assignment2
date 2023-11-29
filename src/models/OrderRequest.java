package models;

import java.util.List;

public class OrderRequest {
    private int customerId;
    private int bookId;
    private int quantity;
    private List<Integer> bookIds;
    private List<Integer> quantities;

//    public OrderRequest(int customerId, List<Integer> bookIds, List<Integer> quantities) {
//        this.customerId = customerId;
//        this.bookIds = bookIds;
//        this.quantities = quantities;
//    }

    public OrderRequest(int customerId, List<Integer> bookIds, List<Integer> quantities) {
        this.customerId = customerId;
        this.bookIds = bookIds;
        this.quantities = quantities;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
}
