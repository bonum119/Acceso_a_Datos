import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GasolineraGestor {

    private List<Cliente> clientes;
    private List<Pago> pagos;
    private int contadorId;
    private int contadorPagoId;

    public GasolineraGestor() {
        this.clientes = new ArrayList<>();
        this.pagos = new ArrayList<>(); // Inicialización agregada
        this.contadorId = 1;
        this.contadorPagoId = 1;
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
        }
    }

    public void mostrarClientes(){
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en el sistema.\n");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
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
            int idNum = Integer.parseInt(id);
            for (Cliente c : clientes){
                if (c.getId() == idNum){
                    System.out.println("Cliente encontrado: ");
                    System.out.println(c);
                    return true;
                }
            }

        System.out.println("No existe el cliente");
        return false;
    }

    public void altaPago(String idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        Pago nuevoPago = new Pago(contadorPagoId++, idCliente, fecha, importe, litros, combustible);
        pagos.add(nuevoPago);
    }

    public void mostrarPagos() {
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados en el sistema.\n");
            return;
        }

        for (Pago p : pagos) {
            System.out.println(p);
        }
        System.out.println();
    }
}