import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemoriaPagoRepositorio implements PagoRepositorio {

    private final List<Pagos> pagos = new ArrayList<>();

    @Override
    public Pagos guardar(int idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        int id = siguienteId();
        Pagos pago = new Pagos(id, idCliente, fecha, importe, litros, combustible);
        pagos.add(pago);
        return pago;
    }

    private int siguienteId() {
        return pagos.stream()
                .mapToInt(Pagos::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Pagos> listarTodos() {
        return new ArrayList<>(pagos);
    }
}
