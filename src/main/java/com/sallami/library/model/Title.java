package com.sallami.library.model;

public record Title(String title) {
    public Title {


        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title cannot be null or black");
        }

        title = title.trim();

        if (title.length() > 100) {
            throw new IllegalArgumentException("the title cannot exceed 100 characters");
        }

    }

}
