package com.sallami.library.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class User {

    private final String firstName;
    private final String lastName;
    private final String userName;
    private Password password;



    public User(String firstName, String lastName, String userName) {
        validateUser(firstName, lastName, userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void updatePassword(Password password) {
        if (password == null){
            throw new IllegalArgumentException("The password cannot be null");
        }
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    private void validateUser(String firstName, String lastName, String userName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        validateUserName(userName);
    }

    private void validateFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("User first name cannot be null or blank");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("User last name cannot be null or blank");
        }
    }

    private void validateUserName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("User userName cannot be null or blank");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("User password cannot be null or blank");
        }
    }


}
