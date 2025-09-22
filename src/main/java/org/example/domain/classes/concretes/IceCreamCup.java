package org.example.domain.classes.concretes;

import org.example.domain.classes.abstracts.MenuItem;
import org.example.domain.enums.CupSize;
import org.example.domain.enums.Topping;
import org.example.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class IceCreamCup extends MenuItem {
    private final CupSize size;
    private final List<Scoop> scoops = new ArrayList<>(); // composición
    private final List<Topping> toppings = new ArrayList<>(); // agregación simple

    public IceCreamCup(String id, String name, double price, CupSize size) {
        super(id, name, price);
        this.size = size;
    }

    public CupSize getSize() {
        return size;
    }

    public List<Scoop> getScoops() {
        return new ArrayList<>(scoops);
    }

    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    public void addScoop(Scoop scoop) {
        scoops.add(scoop);
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (size == null) throw new ValidationException("El tamaño no puede ser null");
        if (scoops.isEmpty()) throw new ValidationException("Debe tener al menos 1 bocha de helado");
        if (scoops.size() > size.getMaxScoops())
            throw new ValidationException("Cantidad de bochas excede el máximo para " + size);
        for (Scoop s : scoops) {
            if (s == null) throw new ValidationException("Bocha null no permitida");
            s.validate();
        }
    }

    @Override
    public double totalPrice() {
        double base = getPrice();
        double toppingsCost = toppings.size() * 0.5; // cada topping suma 0.5
        return base + toppingsCost;
    }

    @Override
    public String summarize() {
        return String.format("Copa %s '%s' - $%.2f", size, getName(), totalPrice());
    }
}
