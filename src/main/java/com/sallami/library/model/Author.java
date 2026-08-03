package com.sallami.library.model;

import java.util.Objects;

public record Author(String firstName, String lastName) {

    public Author {
        if(firstName == null || firstName.isBlank() )  {
            throw new IllegalArgumentException("Author first name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Author last name cannot be null or blank");
        }
    }

}
