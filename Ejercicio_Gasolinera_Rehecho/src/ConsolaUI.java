import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsolaUI {

    Scanner sc = new Scanner(System.in);
    GasolineraGestor gestor = new GasolineraGestor();
    private List clientes;
    LocalDate fecha;

    public void iniciar(){
        boolean terminar = false;

        while (!terminar){
            mostrarmenu();
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    darDeAltaCliente();
                    break;
                case "2":
                    listarClientes();
                    break;
                case "3":
                    buscarClientes();
                    break;
                case "4":
                    procesarPago();
                    break;
                case "5":
                    consultarPagos();
                    break;
                case "0":
                    terminar = true;
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private void consultarPagos() {
        System.out.println("\n=== LISTA DE PAGOS ===");
        gestor.mostrarPagos();
    }

    private void procesarPago() {
        String idCliente = leerTexto("ID del cliente: ");

        while (!gestor.buscarId(idCliente)) {
            idCliente = leerTexto("ID no encontrado, pruebe de nuevo: ");
        }

        LocalDate fecha = leerFecha("Fecha (YYYY-MM-DD, Vacio para hoy): ");
        double importe = leerDouble("Importe: ");
        double litros = leerDouble("Litros: ");
        String combustible = leerTexto("Combustible: ");

        gestor.altaPago(idCliente, fecha, importe, litros, combustible);
    }

    private void buscarClientes() {
        System.out.println("Escriba la matrícula a buscar: ");
        String matricula = sc.nextLine();

        gestor.buscarMatricula(matricula);
    }

    private void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        gestor.mostrarClientes();
    }

    private void darDeAltaCliente() {
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String matricula = leerTexto("Matrícula: ").toUpperCase();

        if (gestor.matriculaRegistrada(matricula.toUpperCase(Locale.ROOT))) {
            System.out.println("Esa matrícula ya está registrada. No se ha creado el cliente.");
            return;
        }

        gestor.altaCliente(nombre, telefono, matricula);
        System.out.println("Cliente registrado correctamente.");
    }

    private String leerTexto(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = sc.nextLine();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo es obligatorio.");
        }
    }

    private double leerDouble(String etiqueta) {
        while (true) {
            try {
                System.out.print(etiqueta);
                double valor = Double.parseDouble(sc.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un número válido.");
            }
        }
    }

    private LocalDate leerFecha(String etiqueta) {
        try {
            System.out.print(etiqueta);
            String entrada = sc.nextLine();
            if (entrada.trim().isEmpty()) {
                System.out.println("Se ha asignado la fecha de hoy.");
                return LocalDate.now();
            }
            return LocalDate.parse(entrada);
        } catch (DateTimeParseException ex){
            System.out.println("Error, se pondrá la fecha actual.");
        }
        return LocalDate.now();
    }

    private void mostrarmenu() {
        System.out.println("=== GESTIÓN DE GASOLINERA ===\n" +
                "1. Dar de alta un cliente\n" +
                "2. Listar clientes\n" +
                "3. Buscar clientes\n" +
                "4. Procesar un pago de repostaje\n" +
                "5. Consultar pagos\n" +
                "0. Salir\n" +
                "Opción:\n");
    }

}