package com.sallami.library.model;

import java.util.Objects;

public class Author {

    private  String firstName;
    private  String lastName;


    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        if(firstName == null || firstName.isBlank() )  {
            throw new IllegalArgumentException("Author first name cannot be null or blank");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Author last name cannot be null or blank");
        }
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Author author)) {
            return false;
        }
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName,lastName);
    }
}
