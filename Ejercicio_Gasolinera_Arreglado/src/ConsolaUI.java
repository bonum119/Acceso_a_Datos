import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsolaUI {

    private final GasolineraApp app;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatoFecha = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public ConsolaUI(GasolineraApp app) {
        this.app = app;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
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
                    salir = true;
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=== GESTIÓN DE GASOLINERA ===");
        System.out.println("1. Dar de alta un cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar clientes");
        System.out.println("4. Procesar un pago de repostaje");
        System.out.println("5. Consultar pagos");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private void darDeAltaCliente() {
        String nombre = leerTextoObligatorio("Nombre: ");
        String telefono = leerTextoObligatorio("Teléfono: ");
        String matricula = leerTextoObligatorio("Matrícula: ").toUpperCase();

        if (app.matriculaRegistrada(matricula)) {
            System.out.println("Esa matrícula ya está registrada. No se ha creado el cliente.");
            return;
        }

        Cliente cliente = app.altaCliente(nombre, telefono, matricula);
        System.out.println("Cliente registrado con ID " + cliente.getId() + ".");
    }

    private void listarClientes() {
        List<Cliente> clientes = app.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        mostrarTablaClientes(clientes);
    }

    private void buscarClientes() {
        String texto = leerTextoObligatorio("Texto que buscar: ");
        List<Cliente> resultado = app.buscarClientes(texto);
        if (resultado.isEmpty()) {
            System.out.println("No se han encontrado clientes.");
            return;
        }
        mostrarTablaClientes(resultado);
    }

    private void mostrarTablaClientes(List<Cliente> clientes) {
        System.out.printf("%-4s%-20s%-15s%-10s%n", "ID", "NOMBRE", "TELÉFONO", "MATRÍCULA");
        for (Cliente cliente : clientes) {
            System.out.printf("%-4d%-20s%-15s%-10s%n",
                    cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getMatricula());
        }
    }

    private void procesarPago() {
        if (!app.hayClientes()) {
            System.out.println("Primero debes dar de alta un cliente.");
            return;
        }

        mostrarTablaClientes(app.listarClientes());
        int idCliente = leerIdCliente();
        Optional<Cliente> cliente = app.buscarClientePorId(idCliente);
        if (cliente.isEmpty()) {
            System.out.println("No existe un cliente con ese identificador. No se ha registrado el pago.");
            return;
        }

        LocalDate fecha = leerFecha();
        double importe = leerCantidad("Importe (€): ");
        double litros = leerCantidad("Litros: ");
        String combustible = leerTextoObligatorio("Combustible: ");

        Pagos pago = app.procesarPago(idCliente, fecha, importe, litros, combustible);
        System.out.printf("Pago %d registrado para %s: %.2f €.%n",
                pago.getId(), cliente.get().getNombre(), pago.getImporte());
    }

    private void consultarPagos() {
        List<Pagos> pagos = app.consultarPagos();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados.");
            return;
        }

        System.out.printf("%-4s%-20s%-12s%-10s%-8s%-15s%n",
                "ID", "CLIENTE", "FECHA", "IMPORTE", "LITROS", "COMBUSTIBLE");
        for (Pagos pago : pagos) {
            String nombreCliente = app.buscarClientePorId(pago.getIdCliente())
                    .map(Cliente::getNombre)
                    .orElse("Desconocido");
            String importeTexto = String.format("%.2f €", pago.getImporte());
            System.out.printf("%-4d%-20s%-12s%-10s%-8.2f%-15s%n",
                    pago.getId(), nombreCliente, pago.getFecha().format(formatoFecha),
                    importeTexto, pago.getLitros(), pago.getCombustible());
        }
    }

    private int leerIdCliente() {
        while (true) {
            System.out.print("ID del cliente: ");
            String entrada = scanner.nextLine().trim();
            try {
                int id = Integer.parseInt(entrada);
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Introduce un número entero positivo.");
        }
    }

    private LocalDate leerFecha() {
        while (true) {
            System.out.print("Fecha (dd/MM/aaaa; vacío para hoy): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(entrada, formatoFecha);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha no es válida.");
            }
        }
    }

    private double leerCantidad(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            if (entrada.matches("\\d+(\\.\\d{1,2})?") && Double.parseDouble(entrada) > 0) {
                return Double.parseDouble(entrada);
            }
            System.out.println("Introduce una cantidad mayor que cero y con un máximo de dos decimales.");
        }
    }

    private String leerTextoObligatorio(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo es obligatorio.");
        }
    }
}
