package org.example.domain;

import org.example.exceptions.ValidationException;

public class Book extends Publication {
    private final String author;
    private final int pages;

    public Book(String id, String title, String author, int pages) {
        super(id, title);
        this.author = author;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (author == null || author.trim().isEmpty()) {
            throw new ValidationException("El autor no puede estar vacío");
        }

        if (pages <= 0) {
            throw new ValidationException("Las páginas deben ser un entero positivo");
        }
    }

    @Override
    public String summarize() {
        return "Libro[id=" + getId() + ", título=\"" + getTitle() + "\"" + ", autor=\"" + author + "\"" + ", páginas=" + pages + "]";
    }
}

