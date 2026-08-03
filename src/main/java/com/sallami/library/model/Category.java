package com.sallami.library.model;

import java.util.Objects;

public record Category(String name) {

    public Category {
        if ( name == null || name.isBlank() ) {
            throw new IllegalArgumentException("Le name de la catégorie ne peut être vide");
        }
    }

}
