import java.util.List;
import java.util.Optional;

public interface ClienteRepositorio {

    Cliente guardar(String nombre, String telefono, String matricula);

    List<Cliente> listarTodos();

    Optional<Cliente> buscarPorId(int id);

    boolean existeMatricula(String matricula);
}
