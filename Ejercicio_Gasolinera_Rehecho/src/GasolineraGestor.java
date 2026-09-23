import java.util.ArrayList;
import java.util.List;

public class GasolineraGestor {

    private List<Cliente> clientes;
    private int contadorId;

    public GasolineraGestor() {
        this.clientes = new ArrayList<>();
        this.contadorId = 1;
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
        for (Cliente c : clientes){
            if (matricula.equalsIgnoreCase(c.getMatricula())){
                System.out.println("Cliente encontrado: ");
                System.out.println(c);
            } else {
                System.out.println("No esta la matrícula registrada");
            }
        }
    }
}