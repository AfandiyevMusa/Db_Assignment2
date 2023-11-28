package models;

import java.time.LocalDate;
import java.util.List;

public class Author {
    private int authorId;

    private String authorName;

    private LocalDate birthDate;

    private String country;

    private List<Book> books;

    public Author(String authorName, LocalDate birthDate, String country) {
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.country = country;
    }

    public Author(int authorId, String authorName, LocalDate birthDate, String country) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.birthDate = birthDate;
        this.country = country;
    }

    @Override
    public String toString() {
        return "AuthorID: " + authorId +
                ", AuthorName: " + authorName +
                ", BirthDate: " + birthDate +
                ", Country: " + country;
    }

    // Getters and Setters
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}