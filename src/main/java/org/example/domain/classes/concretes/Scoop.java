package org.example.domain.classes.concretes;

import org.example.domain.enums.Flavor;
import org.example.domain.interfaces.Summarizable;
import org.example.domain.interfaces.Validatable;
import org.example.exceptions.ValidationException;

// Scoop = bocha de helado
public class Scoop implements Summarizable, Validatable {
    private final Flavor flavor;

    public Scoop(Flavor flavor) {
        this.flavor = flavor;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    @Override
    public void validate() throws ValidationException {
        if (flavor == null) {
            throw new ValidationException("El sabor no puede ser null");
        }
    }

    @Override
    public String summarize() {
        return flavor.name();
    }
}

