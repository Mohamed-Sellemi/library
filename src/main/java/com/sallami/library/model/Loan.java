package com.sallami.library.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Loan {

    private final UUID idLoan;
    private final User user;
    private final Book book;
    private final LocalDate dateEmprunt;
    private final LocalDate dateRenduPrevu;
    private LocalDate dateRenduEffective;

    public Loan(User user, Book book) {
        validateLoan(user, book);
        this.idLoan = UUID.randomUUID();
        this.user = user;
        this.book = book;
        this.dateEmprunt = LocalDate.now();
        this.dateRenduPrevu = LocalDate.now().plusDays(15);
    }

    public void returnBook() {
        if (dateRenduEffective != null) {
            throw new IllegalStateException("Book déjà rendu");
        }
        this.dateRenduEffective = LocalDate.now();
    }

    public boolean estEnRetard() {
        if (dateRenduEffective != null) {
            return dateRenduEffective.isAfter(dateRenduPrevu);
        }
        return LocalDate.now().isAfter(dateRenduPrevu);
    }

    public UUID getIdLoan() {
        return idLoan;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRenduPrevu() {
        return dateRenduPrevu;
    }

    public Optional<LocalDate> getDateRenduEffective() {
        return Optional.ofNullable(dateRenduEffective);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Loan loan)) return false;
        return Objects.equals(idLoan, loan.idLoan);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idLoan);
    }


    @Override
    public String toString() {
        return "Loan{" +
                "idLoan=" + idLoan +
                ", user=" + user +
                ", book=" + book +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRenduPrevu=" + dateRenduPrevu +
                ", dateRenduEffective=" + dateRenduEffective +
                '}';
    }

    private void validateLoan(User user, Book book) {
        validateUser(user);
        validateBook(book);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("The book cannot be null");
        }
    }

}
