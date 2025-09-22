package org.example.domain.classes.concretes;

import org.example.domain.classes.abstracts.MenuItem;
import org.example.domain.interfaces.Summarizable;
import org.example.domain.interfaces.Validatable;
import org.example.exceptions.ValidationException;

public class OrderLine implements Summarizable, Validatable {
    private final MenuItem item; // asociación
    private final int quantity;

    public OrderLine(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double lineTotal() {
        return item.totalPrice() * quantity;
    }

    @Override
    public void validate() throws ValidationException {
        if (item == null) throw new ValidationException("El item no puede ser null");
        if (quantity <= 0) throw new ValidationException("La cantidad debe ser > 0");
        item.validate();
    }

    @Override
    public String summarize() {
        return String.format("%dx %s = $%.2f", quantity, item.summarize(), lineTotal());
    }
}

