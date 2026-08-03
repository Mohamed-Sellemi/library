package com.sallami.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {

    private Title title;
    private final String isbn;
    private final LocalDate publicationDate;
    private final List<Author> authors;
    private Category category;

    public Book(Title title, String isbn, LocalDate publicationDate, List<Author> authors, Category category) {
        validateBook( isbn, publicationDate, authors, category);
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.authors = new ArrayList<>(authors);
        this.category = category;
    }

    public Title getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public List<Author> getAuthors() {
        return List.copyOf(authors);
    }

    public Category getCategory() {
        return category;
    }

    public void rename(Title title) {
        this.title = title;
    }

    public void addAuthor(Author author) {
        validateAuthor(author);
        if (authorExists(author)) {
            throw new IllegalArgumentException("Author already exist ... ");
        }
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        validateAuthor(author);
        if (!authorExists(author)) {
            throw new IllegalArgumentException("Author does not belong to this book. ");
        }
        this.authors.remove(author);
    }

    public void changeCategory(Category category) {
        validateCategory(category);
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", category=" + category +
                '}';
    }

    private void validateBook(String isbn, LocalDate publicationDate, List<Author> authors, Category category) {

        validateIsbn(isbn);
        validatePublicationDate(publicationDate);
        validateAuthors(authors);
        validateCategory(category);
    }



    private void validateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("Book isbn cannot be null or black");
        }
    }

    private void validatePublicationDate(LocalDate publicationDate) {
        if (publicationDate == null || publicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Book publish date cannot be null or in future");
        }
    }

    private void validateAuthors(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            throw new IllegalArgumentException("Book authors  cannot be null or empty");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Book category  cannot be null");
        }
    }

    private boolean authorExists(Author author) {
        return authors.contains(author);
    }

    private void validateAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Book author  cannot be null");
        }
    }
}
