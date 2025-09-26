package org.example.domain.classes.concretes;

import org.example.domain.enums.OrderStatus;
import org.example.domain.interfaces.Identifiable;
import org.example.domain.interfaces.Summarizable;
import org.example.domain.interfaces.Validatable;
import org.example.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order implements Identifiable, Summarizable, Validatable {
    private final String id;
    private final Customer customer; // asociación
    private final List<OrderLine> lines = new ArrayList<>(); // composición
    private OrderStatus status = OrderStatus.NUEVO;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Order(Customer customer) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
    }

    @Override
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void confirm() {
        if (status == OrderStatus.NUEVO) status = OrderStatus.CONFIRMADO;
    }

    public void cancel() {
        if (status != OrderStatus.CANCELADO) status = OrderStatus.CANCELADO;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public double total() {
        double sum = 0.0;
        for (int i = 0; i < lines.size(); i++) {
            OrderLine l = lines.get(i);
            if (l != null) sum += l.lineTotal();
        }
        return sum;
    }

    @Override
    public void validate() throws ValidationException {
        if (customer == null) throw new ValidationException("El cliente no puede ser null");
        customer.validate();
        if (lines.isEmpty()) throw new ValidationException("El pedido debe tener al menos una línea");
        for (OrderLine l : lines) {
            if (l == null) throw new ValidationException("Línea null no permitida");
            l.validate();
        }
    }

    public OrderLine[] linesArray() {
        return lines.toArray(new OrderLine[0]);
    }

    @Override
    public String summarize() {
        StringBuilder items = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) items.append(" | ");
            OrderLine l = lines.get(i);
            items.append(l != null ? l.summarize() : "(línea inválida)");
        }
        return String.format("Pedido %s [%s] de %s => $%.2f :: %s", id, status, customer.getName(), total(), items);
    }
}
