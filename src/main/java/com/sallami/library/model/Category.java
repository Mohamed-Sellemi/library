package com.sallami.library.model;

import java.util.Objects;

public class Category {

    private final String name;

    public Category(String name) {
        if ( name == null || name.isBlank() ) {
            throw new IllegalArgumentException("Le name de la catégorie ne peut être vide");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Categorie { name='  "+name+"' }";
    }
}
