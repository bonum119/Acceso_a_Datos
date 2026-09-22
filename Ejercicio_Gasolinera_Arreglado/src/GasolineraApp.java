import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GasolineraApp {

    private final ClienteRepositorio clienteRepositorio;
    private final PagoRepositorio pagoRepositorio;

    public GasolineraApp(ClienteRepositorio clienteRepositorio, PagoRepositorio pagoRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.pagoRepositorio = pagoRepositorio;
    }

    public boolean matriculaRegistrada(String matricula) {
        return clienteRepositorio.existeMatricula(matricula);
    }

    public Cliente altaCliente(String nombre, String telefono, String matricula) {
        return clienteRepositorio.guardar(nombre, telefono, matricula);
    }

    public boolean hayClientes() {
        return !clienteRepositorio.listarTodos().isEmpty();
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.listarTodos();
        clientes.sort(Comparator
                .comparing((Cliente cliente) -> cliente.getNombre().toLowerCase())
                .thenComparingInt(Cliente::getId));
        return clientes;
    }

    public List<Cliente> buscarClientes(String texto) {
        String textoBusqueda = texto.toLowerCase();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : listarClientes()) {
            if (cliente.getNombre().toLowerCase().contains(textoBusqueda)
                    || cliente.getTelefono().toLowerCase().contains(textoBusqueda)
                    || cliente.getMatricula().toLowerCase().contains(textoBusqueda)) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    public Optional<Cliente> buscarClientePorId(int id) {
        return clienteRepositorio.buscarPorId(id);
    }

    public Pagos procesarPago(int idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        return pagoRepositorio.guardar(idCliente, fecha, importe, litros, combustible);
    }

    public List<Pagos> consultarPagos() {
        List<Pagos> pagos = pagoRepositorio.listarTodos();
        pagos.sort(Comparator
                .comparing(Pagos::getFecha)
                .thenComparingInt(Pagos::getId)
                .reversed());
        return pagos;
    }
}
