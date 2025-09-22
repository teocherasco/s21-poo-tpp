package org.example.repo.concrete;

import org.example.domain.classes.concretes.Customer;
import org.example.exceptions.DuplicateException;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.ValidationException;
import org.example.repo.interfaces.Repository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepository implements Repository<Customer> {
    private final List<Customer> storage = new ArrayList<>();

    @Override
    public void add(Customer entity) throws ValidationException, DuplicateException {
        if (entity == null) throw new ValidationException("El cliente no puede ser null");
        entity.validate();
        if (indexOfId(entity.getId()) != -1) {
            throw new DuplicateException("Ya existe un cliente con ID '" + entity.getId() + "'");
        }
        storage.add(entity);
    }

    @Override
    public Customer[] findAll() {
        return storage.toArray(new Customer[0]);
    }

    @Override
    public Customer findById(String id) throws NotFoundException {
        int idx = indexOfId(id);
        if (idx == -1) throw new NotFoundException("No se encontró cliente con ID '" + id + "'");
        return storage.get(idx);
    }

    @Override
    public void removeById(String id) throws NotFoundException {
        int idx = indexOfId(id);
        if (idx == -1) throw new NotFoundException("No se encontró cliente con ID '" + id + "'");
        storage.remove(idx);
    }

    @Override
    public int size() {
        return storage.size();
    }

    private int indexOfId(String id) {
        if (id == null) return -1;
        for (int i = 0; i < storage.size(); i++) {
            if (id.equals(storage.get(i).getId())) return i;
        }
        return -1;
    }
}

