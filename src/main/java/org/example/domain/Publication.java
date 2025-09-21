package org.example.domain;

import org.example.exceptions.ValidationException;

public abstract class Publication implements Identifiable, Summarizable, Validatable {
    private final String id;
    private final String title;

    protected Publication(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void validate() throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("El ID no puede estar vacío");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("El título no puede estar vacío");
        }

        if (id.length() > 50) {
            throw new ValidationException("El ID excede el máximo de 50 caracteres");
        }

        if (title.length() > 200) {
            throw new ValidationException("El título excede el máximo de 200 caracteres");
        }
    }
}

