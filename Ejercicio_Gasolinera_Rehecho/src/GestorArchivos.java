import java.util.List;

public interface GestorArchivos {
    List<Cliente> leerClientes();
    void guardarClientes(List<Cliente> clientes);
    List<Pago> leerPagos();
    void guardarPagos(List<Pago> pagos);
}