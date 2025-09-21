package org.example.repo;

import org.example.domain.Publication;
import org.example.exceptions.DuplicateException;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.RepositoryFullException;
import org.example.exceptions.ValidationException;

import java.util.Arrays;

public class PublicationRepository {
    private final Publication[] storage;
    private int size = 0;

    public PublicationRepository(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser > 0");
        }
        this.storage = new Publication[capacity];
    }

    public int capacity() {
        return storage.length;
    }

    public int size() {
        return size;
    }

    public void add(Publication publication) throws ValidationException, DuplicateException, RepositoryFullException {
        if (publication == null) {
            throw new ValidationException("La publicación no puede ser null");
        }

        publication.validate();

        if (size >= storage.length) {
            throw new RepositoryFullException("No hay espacio: capacidad=" + storage.length);
        }

        String id = publication.getId();

        if (indexOfId(id) != -1) {
            throw new DuplicateException("Ya existe una publicación con ID '" + id + "'");
        }

        storage[size++] = publication;
    }

    public Publication[] findAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public Publication findById(String id) throws NotFoundException {
        int idx = indexOfId(id);
        if (idx == -1) {
            throw new NotFoundException("No se encontró publicación con ID '" + id + "'");
        }

        return storage[idx];
    }

    public void removeById(String id) throws NotFoundException {
        int idx = indexOfId(id);
        if (idx == -1) {
            throw new NotFoundException("No se encontró publicación con ID '" + id + "'");
        }

        for (int i = idx; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }

        storage[--size] = null;
    }

    private int indexOfId(String id) {
        if (id == null) return -1;
        for (int i = 0; i < size; i++) {
            Publication p = storage[i];
            if (p != null && id.equals(p.getId())) {
                return i;
            }
        }
        return -1;
    }
}

