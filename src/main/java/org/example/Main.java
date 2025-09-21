package org.example;

import org.example.domain.Book;
import org.example.domain.Magazine;
import org.example.domain.Publication;
import org.example.exceptions.*;
import org.example.repo.PublicationRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private final Scanner scanner = new Scanner(System.in);
    private final PublicationRepository repository = new PublicationRepository(100);

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();

            int option = readInt("Elige una opción: ");
            switch (option) {
                case 1 -> addBook();
                case 2 -> addMagazine();
                case 3 -> listAll();
                case 4 -> findById();
                case 5 -> removeById();
                case 0 -> {
                    System.out.println("Terminando programa...");
                    running = false;
                }
                default -> System.out.println("Opción inválida. Intenta de nuevo.");
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("==== Biblioteca CLI ====");
        System.out.println("1) Agregar Libro");
        System.out.println("2) Agregar Revista");
        System.out.println("3) Listar Publicaciones");
        System.out.println("4) Buscar por ID");
        System.out.println("5) Eliminar por ID");
        System.out.println("0) Salir");
    }

    private void addBook() {
        System.out.println("-- Nuevo Libro --");
        String id = readNonEmpty("ID: ");
        String title = readNonEmpty("Título: ");
        String author = readNonEmpty("Autor: ");
        int pages = readPositiveInt("Páginas: ");

        Publication book = new Book(id, title, author, pages);
        try {
            repository.add(book);
            System.out.println("Libro agregado correctamente.");
        } catch (ValidationException | DuplicateException | RepositoryFullException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addMagazine() {
        System.out.println("-- Nueva Revista --");
        String id = readNonEmpty("ID: ");
        String title = readNonEmpty("Título: ");
        int issueNumber = readPositiveInt("Número de edición: ");

        Publication magazine = new Magazine(id, title, issueNumber);
        try {
            repository.add(magazine);
            System.out.println("Revista agregada correctamente.");
        } catch (ValidationException | DuplicateException | RepositoryFullException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAll() {
        System.out.println("-- Publicaciones --");
        Publication[] items = repository.findAll();
        if (items.length == 0) {
            System.out.println("(vacío)");
            return;
        }
        for (Publication p : items) {
            System.out.println("- " + p.summarize());
        }
    }

    private void findById() {
        String id = readNonEmpty("ID a buscar: ");
        try {
            Publication p = repository.findById(id);
            System.out.println("Encontrado: " + p.summarize());
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    private void removeById() {
        String id = readNonEmpty("ID a eliminar: ");
        try {
            repository.removeById(id);
            System.out.println("Eliminado correctamente.");
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingresa un número entero.");
            }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) return value;
            System.out.println("Debe ser un entero positivo.");
        }
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("No puede estar vacío.");
        }
    }
}
