import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class GasolineraGestor {

    private List<Cliente> clientes;
    private List<Pago> pagos;
    private int contadorId;
    private int contadorPagoId;
    private final GestorArchivos gestorArchivos;

    public GasolineraGestor() {
        this.gestorArchivos = new GestorArchivosCSV();
        this.clientes = gestorArchivos.leerClientes();
        this.pagos = gestorArchivos.leerPagos();
        this.contadorId = calcularSiguienteId();
        this.contadorPagoId = calcularSiguientePagoId();
    }

    private int calcularSiguienteId() {
        int maximo = 0;
        for (Cliente c : clientes) {
            if (c.getId() > maximo) {
                maximo = c.getId();
            }
        }
        return maximo + 1;
    }

    private int calcularSiguientePagoId() {
        int maximo = 0;
        for (Pago p : pagos) {
            if (p.getId() > maximo) {
                maximo = p.getId();
            }
        }
        return maximo + 1;
    }

    public boolean matriculaRegistrada(String matricula) {
        if (matricula == null) {
            return false;
        }

        for (Cliente c : clientes) {
            if (matricula.equalsIgnoreCase(c.getMatricula())) {
                return true;
            }
        }
        return false;
    }

    public void altaCliente(String nombre, String telefono, String matricula) {
        if (matriculaRegistrada(matricula)) {
            System.out.println("La matrícula ya está registrada.");

        } else {
            Cliente nuevoCliente = new Cliente(contadorId++, nombre, telefono, matricula);
            clientes.add(nuevoCliente);
            gestorArchivos.guardarClientes(clientes);
        }
    }

    public void mostrarClientes(){
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema.\n");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println(c);
        }
        System.out.println();
    }

    public void buscarMatricula(String matricula) {
        boolean encontrado = false;
        for (Cliente c : clientes){
            if (matricula.equalsIgnoreCase(c.getMatricula())){
                System.out.println("Cliente encontrado: ");
                System.out.println(c);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No esta la matrícula registrada");
        }
    }

    public boolean buscarId(String id) {
        try {
            int idNum = Integer.parseInt(id);
            for (Cliente c : clientes) {
                if (c.getId() == idNum) {
                    System.out.println("Cliente encontrado: ");
                    System.out.println(c);
                    return true;
                }
            }
        } catch (NumberFormatException ex){
            return false;
        }
        return false;
    }

    public void altaPago(String idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        Pago nuevoPago = new Pago(contadorPagoId++, idCliente, fecha, importe, litros, combustible);
        pagos.add(nuevoPago);
        gestorArchivos.guardarPagos(pagos);
    }

    public void mostrarPagos() {
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados en el sistema.\n");
            return;
        }

        for (Pago p : pagos) {
            String nombreCliente = obtenerNombreCliente(p.getIdCliente());
            System.out.printf("ID: %d Cliente: %s Fecha: %s Importe: %.2f € Litros: %.2f Combustible: %s%n",
                    p.getId(), nombreCliente, p.getFecha(), p.getImporte(), p.getLitros(), p.getCombustible());
        }
        System.out.println();
    }

    private String obtenerNombreCliente(String idCliente) {
        int idNum = Integer.parseInt(idCliente);
        for (Cliente c : clientes) {
            if (c.getId() == idNum) {
                return c.getNombre();
            }
        }
        return "Desconocido";
    }
}