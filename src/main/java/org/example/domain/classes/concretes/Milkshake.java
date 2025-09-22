package org.example.domain.classes.concretes;

import org.example.domain.classes.abstracts.MenuItem;
import org.example.domain.enums.CupSize;
import org.example.domain.enums.Flavor;
import org.example.exceptions.ValidationException;

public class Milkshake extends MenuItem {
    private final Flavor flavor;
    private final CupSize size;

    public Milkshake(String id, String name, double price, Flavor flavor, CupSize size) {
        super(id, name, price);
        this.flavor = flavor;
        this.size = size;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public CupSize getSize() {
        return size;
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (flavor == null) throw new ValidationException("El sabor no puede ser null");
        if (size == null) throw new ValidationException("El tamaño no puede ser null");
    }

    @Override
    public String summarize() {
        return String.format("Milkshake %s '%s' (%s) - $%.2f", size, getName(), flavor, totalPrice());
    }
}

