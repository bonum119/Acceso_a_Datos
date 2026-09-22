import java.time.LocalDate;
import java.util.List;

public interface PagoRepositorio {

    Pagos guardar(int idCliente, LocalDate fecha, double importe, double litros, String combustible);

    List<Pagos> listarTodos();
}
