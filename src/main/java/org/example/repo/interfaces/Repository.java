package org.example.repo.interfaces;

import org.example.domain.interfaces.Identifiable;
import org.example.exceptions.DuplicateException;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.ValidationException;

public interface Repository<T extends Identifiable> {
    void add(T entity) throws ValidationException, DuplicateException;
    T[] findAll();
    T findById(String id) throws NotFoundException;
    void removeById(String id) throws NotFoundException;
    int size();
}

