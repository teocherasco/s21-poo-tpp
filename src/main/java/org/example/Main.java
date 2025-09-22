package org.example;

import org.example.domain.classes.concretes.*;
import org.example.domain.enums.CupSize;
import org.example.domain.enums.Flavor;
import org.example.domain.enums.Topping;
import org.example.exceptions.*;
import org.example.repo.concrete.InMemoryCustomerRepository;
import org.example.repo.concrete.InMemoryOrderRepository;
import org.example.repo.interfaces.Repository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private final Scanner scanner = new Scanner(System.in);
    // Inyección por interfaz
    private final Repository<Customer> customers = new InMemoryCustomerRepository();
    private final Repository<Order> orders = new InMemoryOrderRepository();

    private void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int option = readInt("Elige una opción: ");
            switch (option) {
                case 1 -> customersMenu();
                case 2 -> createOrder();
                case 3 -> listOrders();
                case 4 -> viewOrderById();
                case 5 -> cancelOrderById();
                case 0 -> {
                    System.out.println("Terminando programa...");
                    running = false;
                }
                default -> System.out.println("Opción inválida. Intenta de nuevo.");
            }
            System.out.println();
        }
    }

    private void printMainMenu() {
        System.out.println("==== Heladería CLI ====");
        System.out.println("1) Clientes");
        System.out.println("2) Crear Pedido");
        System.out.println("3) Listar Pedidos");
        System.out.println("4) Ver Pedido por ID");
        System.out.println("5) Cancelar Pedido por ID");
        System.out.println("0) Salir");
    }

    // Clientes
    private void customersMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("-- Clientes --");
            System.out.println("1) Agregar cliente");
            System.out.println("2) Listar clientes");
            System.out.println("3) Buscar cliente por ID");
            System.out.println("4) Eliminar cliente por ID");
            System.out.println("0) Volver");
            int opt = readInt("Opción: ");
            switch (opt) {
                case 1 -> addCustomer();
                case 2 -> listCustomers();
                case 3 -> findCustomerById();
                case 4 -> removeCustomerById();
                case 0 -> back = true;
                default -> System.out.println("Opción inválida");
            }
            System.out.println();
        }
    }

    private void addCustomer() {
        System.out.println("-- Nuevo Cliente --");
        String id = readNonEmpty("ID: ");
        String name = readNonEmpty("Nombre: ");
        String phone = readNonEmpty("Teléfono: ");
        try {
            customers.add(new Customer(id, name, phone));
            System.out.println("Cliente agregado.");
        } catch (ValidationException | DuplicateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listCustomers() {
        System.out.println("-- Clientes --");
        Customer[] all = customers.findAll();
        if (all.length == 0) {
            System.out.println("(vacío)");
            return;
        }
        for (Customer c : all) System.out.println("- " + c.summarize());
    }

    private void findCustomerById() {
        String id = readNonEmpty("ID de cliente: ");
        try {
            Customer c = customers.findById(id);
            System.out.println("Encontrado: " + c.summarize());
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    private void removeCustomerById() {
        String id = readNonEmpty("ID a eliminar: ");
        try {
            customers.removeById(id);
            System.out.println("Eliminado.");
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    // Pedidos
    private void createOrder() {
        System.out.println("-- Crear Pedido --");
        Customer customer = pickOrCreateCustomer();
        if (customer == null) return;

        Order order = new Order(null, customer);
        boolean adding = true;
        while (adding) {
            System.out.println("Agregar línea:");
            System.out.println("1) Copa de helado");
            System.out.println("2) Licuado");
            System.out.println("0) Terminar líneas");
            int opt = readInt("Opción: ");
            switch (opt) {
                case 1 -> addIceCreamCupLine(order);
                case 2 -> addMilkshakeLine(order);
                case 0 -> adding = false;
                default -> System.out.println("Opción inválida");
            }
        }

        try {
            order.validate();
            orders.add(order);
            System.out.printf("Pedido creado con ID %s. Total: $%.2f%n", order.getId(), order.total());
        } catch (ValidationException | DuplicateException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private Customer pickOrCreateCustomer() {
        if (customers.size() == 0) {
            System.out.println("No hay clientes. Debes crear uno.");
            addCustomer();
        }
        if (customers.size() == 0) return null; // si falló la creación

        System.out.println("Selecciona cliente existente o crea uno nuevo:");
        System.out.println("1) Elegir existente");
        System.out.println("2) Crear nuevo");
        int opt = readInt("Opción: ");
        if (opt == 2) {
            addCustomer();
        }

        Customer[] all = customers.findAll();
        for (int i = 0; i < all.length; i++) {
            System.out.printf("%d) %s%n", i + 1, all[i].summarize());
        }
        int idx = readIntInRange("Selecciona #: ", 1, all.length) - 1;
        return all[idx];
    }

    private void addIceCreamCupLine(Order order) {
        System.out.println("-- Copa de helado --");
        String id = readNonEmpty("ID de ítem: ");
        String name = readNonEmpty("Nombre: ");
        CupSize size = pickCupSize();
        double basePrice = switch (size) {
            case CHICO -> 2.0;
            case MEDIANO -> 3.0;
            case GRANDE -> 4.0;
        };
        IceCreamCup cup = new IceCreamCup(id, name, basePrice, size);
        int scoops = readIntInRange("Cantidad de bochas (1-" + size.getMaxScoops() + "): ", 1, size.getMaxScoops());
        for (int i = 1; i <= scoops; i++) {
            Flavor f = pickFlavor("Sabor bocha " + i + ": ");
            cup.addScoop(new Scoop(f));
        }
        System.out.println("¿Desea agregar toppings? (s/n)");
        if (readYesNo()) {
            boolean more = true;
            while (more) {
                Topping t = pickTopping("Topping: ");
                cup.addTopping(t);
                System.out.println("¿Agregar otro topping? (s/n)");
                more = readYesNo();
            }
        }
        int qty = readIntInRange("Cantidad: ", 1, 100);
        order.addLine(new OrderLine(cup, qty));
        System.out.println("Línea agregada: " + cup.summarize());
    }

    private void addMilkshakeLine(Order order) {
        System.out.println("-- Licuado --");
        String id = readNonEmpty("ID de ítem: ");
        String name = readNonEmpty("Nombre: ");
        CupSize size = pickCupSize();
        Flavor flavor = pickFlavor("Sabor: ");
        double price = switch (size) {
            case CHICO -> 2.5;
            case MEDIANO -> 3.5;
            case GRANDE -> 4.5;
        };
        Milkshake ms = new Milkshake(id, name, price, flavor, size);
        int qty = readIntInRange("Cantidad: ", 1, 100);
        order.addLine(new OrderLine(ms, qty));
        System.out.println("Línea agregada: " + ms.summarize());
    }

    private void listOrders() {
        System.out.println("-- Pedidos --");
        Order[] all = orders.findAll();
        if (all.length == 0) {
            System.out.println("(vacío)");
            return;
        }
        for (Order o : all) System.out.println("- " + o.summarize());
    }

    private void viewOrderById() {
        String id = readNonEmpty("ID de pedido: ");
        try {
            Order o = orders.findById(id);
            System.out.println(o.summarize());
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    private void cancelOrderById() {
        String id = readNonEmpty("ID de pedido: ");
        try {
            Order o = orders.findById(id);
            o.cancel();
            System.out.println("Pedido cancelado: " + o.getId());
        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        }
    }

    // Inputs de usuario
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

    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) return value;
            System.out.printf("Debe estar entre %d y %d.%n", min, max);
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

    private boolean readYesNo() {
        while (true) {
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim().toLowerCase();
            if (s.equals("s") || s.equals("si") || s.equals("sí") || s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.print("Por favor responde s/n: ");
        }
    }

    private CupSize pickCupSize() {
        CupSize[] sizes = CupSize.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.printf("%d) %s (max %d bochas)%n", i + 1, sizes[i], sizes[i].getMaxScoops());
        }
        int idx = readIntInRange("Tamaño #: ", 1, sizes.length) - 1;
        return sizes[idx];
    }

    private Flavor pickFlavor(String prompt) {
        Flavor[] options = Flavor.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d) %s%n", i + 1, options[i]);
        }
        int idx = readIntInRange(prompt, 1, options.length) - 1;
        return options[idx];
    }

    private Topping pickTopping(String prompt) {
        Topping[] options = Topping.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d) %s%n", i + 1, options[i]);
        }
        int idx = readIntInRange(prompt, 1, options.length) - 1;
        return options[idx];
    }
}
