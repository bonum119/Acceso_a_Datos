import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoriaRepositorio implements ClienteRepositorio {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public Cliente guardar(String nombre, String telefono, String matricula) {
        int id = siguienteId();
        Cliente cliente = new Cliente(id, nombre, telefono, matricula);
        clientes.add(cliente);
        return cliente;
    }

    private int siguienteId() {
        return clientes.stream()
                .mapToInt(Cliente::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst();
    }

    @Override
    public boolean existeMatricula(String matricula) {
        return clientes.stream()
                .anyMatch(cliente -> cliente.getMatricula().equalsIgnoreCase(matricula));
    }
}
