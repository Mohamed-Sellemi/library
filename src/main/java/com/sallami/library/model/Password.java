package com.sallami.library.model;


import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

public class Password {

    private String hashPassword; // TODO : utiliser BCrypt pour hasher le password et ne pas laisser en clair
    private final LocalDate dateCreated;
    private LocalDate dateExpired;
    private LocalDate dateUpdated;
    private final Deque<String> history = new ArrayDeque<>();

    public Password ( String hashPassword) {
        validate(hashPassword);
        this.hashPassword = hashPassword;
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
        this.dateExpired = LocalDate.now().plusMonths(3);
        history.add(hashPassword);
    }

    public void changePassword(String password) {
        validate(password);
        checkPasswordInHistory(password);
        this.hashPassword = password;
        this.dateUpdated = LocalDate.now();
        this.dateExpired = LocalDate.now().plusMonths(3);
        history.addLast(password);
        purgeHistory();
    }

    public boolean isExpired(){
        return dateExpired.isBefore(LocalDate.now());
    }

    public boolean match(String password) {
        return Objects.equals(hashPassword, password);
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getDateExpired() {
        return dateExpired;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Password password1)) return false;
        return Objects.equals(hashPassword, password1.hashPassword) && Objects.equals(dateCreated, password1.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashPassword, dateCreated);
    }

    @Override
    public String toString() {
        return "Password{" +
                ", dateCreated=" + dateCreated +
                ", dateExpired=" + dateExpired +
                ", dateUpdated=" + dateUpdated +
                '}';
    }

    private void validate(String password) {
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException("The password cannot be null or blank");
        }
        if ( password.length() < 10) {
            throw  new IllegalArgumentException("The size of password cannot be less then 10 char");
        }
    }

    private void checkPasswordInHistory(String password) {

        if(this.history.contains(password)){
            throw new IllegalStateException("You can't enter this password, it already exists in history");
        }
    }

    private void purgeHistory(){
        if ( !history.isEmpty() && history.size() > 5) {
            history.removeFirst();
        }
    }
}
