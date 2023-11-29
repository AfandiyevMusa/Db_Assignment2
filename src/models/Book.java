package models;

import java.util.List;

public class Book {

    public Book(int bookId, String title, String genre, double price, int stockQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Book(int bookId, String title, String genre, double price, int stockQuantity, Author author, Order order) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.order = order;
    }

    public Book(int bookId, String title, String genre, double price, int stockQuantity, Author author) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
    }


    public Book(String title, String genre, double price, int stockQuantity, int authorId) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = new Author(authorId, null, null, null); // Create a dummy Author with the given authorId
    }


    @Override
    public String toString() {
        return "BookID: " + bookId +
                ", Title: " + title +
                ", Genre: " + genre +
                ", Price: " + price +
                ", StockQuantity: " + stockQuantity +
                ", Author: " + author +
                ", Order: " + order;
    }

    private int bookId;
    private String title;
    private String genre;
    private double price;
    private int stockQuantity;

    private Author author;
    private Order order;

    // Getters and Setters...

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}