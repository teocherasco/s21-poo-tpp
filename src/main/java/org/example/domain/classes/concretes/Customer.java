package org.example.domain.classes.concretes;

import org.example.domain.interfaces.Identifiable;
import org.example.domain.interfaces.Summarizable;
import org.example.domain.interfaces.Validatable;
import org.example.exceptions.ValidationException;

public class Customer implements Identifiable, Summarizable, Validatable {
    private final String id;
    private final String name;
    private final String phone;

    public Customer(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public void validate() throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("El ID de cliente no puede estar vacío");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("El teléfono no puede estar vacío");
        }
        if (id.length() > 50) throw new ValidationException("ID de cliente demasiado largo");
        if (name.length() > 200) throw new ValidationException("Nombre demasiado largo");
        if (phone.length() > 30) throw new ValidationException("Teléfono demasiado largo");
    }

    @Override
    public String summarize() {
        return String.format("%s - %s (%s)", id, name, phone);
    }
}

