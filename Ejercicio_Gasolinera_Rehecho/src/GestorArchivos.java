import java.util.List;

public interface GestorArchivos {
    List<Cliente> leerClientes();
    boolean guardarClientes(List<Cliente> clientes);
    List<Pago> leerPagos();
    void guardarPagos(List<Pago> pagos);
}