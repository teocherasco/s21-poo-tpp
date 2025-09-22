package org.example.domain.classes.abstracts;

import org.example.domain.interfaces.Identifiable;
import org.example.domain.interfaces.Summarizable;
import org.example.domain.interfaces.Validatable;
import org.example.exceptions.ValidationException;

public abstract class MenuItem implements Identifiable, Summarizable, Validatable {
    private final String id;
    private final String name;
    private final double price;

    protected MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double totalPrice() {
        return price;
    }

    @Override
    public void validate() throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("El ID no puede estar vacío");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío");
        }
        if (id.length() > 50) {
            throw new ValidationException("El ID excede el máximo de 50 caracteres");
        }
        if (name.length() > 200) {
            throw new ValidationException("El nombre excede el máximo de 200 caracteres");
        }
        if (!(price >= 0)) {
            throw new ValidationException("El precio debe ser >= 0");
        }
    }
}
