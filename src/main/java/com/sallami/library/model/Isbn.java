package com.sallami.library.model;

public record Isbn(String isbn) {

    public Isbn {

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("Book isbn cannot be null or black");
        }

        isbn = isbn.trim();

        if(!isbn.startsWith("ISBN")) {
            throw new IllegalArgumentException("Book isbn must be started with ISBN");
        }

        if(isbn.length() != 13) {
            throw new IllegalArgumentException("Book isbn must be have 13 characteres");
        }

    }
}
