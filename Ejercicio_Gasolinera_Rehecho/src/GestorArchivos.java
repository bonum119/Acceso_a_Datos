import java.util.List;

public interface GestorArchivos {
    List leerClientes();
    void guardarClientes(List clientes);
    List leerPagos();
    void guardarPagos(List pagos);
}