import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivosCSV implements GestorArchivos {

    private final Path rutaClientes = Path.of("datos", "clientes.csv");
    private final Path rutaPagos = Path.of("datos", "pagos.csv");

    public GestorArchivosCSV() {
        asegurarRuta();
    }

    private void asegurarRuta() {
        try {
            Path directorio = Path.of("datos");
            if (Files.notExists(directorio)) {
                Files.createDirectories(directorio);
            }
            if (Files.notExists(rutaClientes)) {
                Files.createFile(rutaClientes);
            }
            if (Files.notExists(rutaPagos)) {
                Files.createFile(rutaPagos);
            }
        } catch (IOException e) {
            System.err.println("Error al crear la estructura de archivos: " + e.getMessage());
        }
    }

    @Override
    public List leerClientes() {
        List clientes = new ArrayList<>();
        if (Files.notExists(rutaClientes)) return clientes;

        try (BufferedReader lector = Files.newBufferedReader(rutaClientes)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split(";");
                if (campos.length >= 4) {
                    try {
                        int id = Integer.parseInt(campos[0].trim());
                        String nombre = campos[1].trim();
                        String telefono = campos[2].trim();
                        String matricula = campos[3].trim();
                        clientes.add(new Cliente(id, nombre, telefono, matricula));
                    } catch (NumberFormatException e) {
                        System.err.println("Error en el formato numerico de la linea: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en clientes: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public void guardarClientes(List clientes) {
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaClientes)) {
            for (Cliente c : clientes) {
                String linea = c.getId() + ";" + c.getNombre() + ";" + c.getTelefono() + ";" + c.getMatricula();
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    @Override
    public List leerPagos() {
        List pagos = new ArrayList<>();
        if (Files.notExists(rutaPagos)) return pagos;

        try (BufferedReader lector = Files.newBufferedReader(rutaPagos)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split(";");
                if (campos.length >= 4) {
                    try {
                        int id = Integer.parseInt(campos[0].trim());
                        int idCliente = Integer.parseInt(campos[1].trim());
                        double monto = Double.parseDouble(campos[2].trim());
                        LocalDate fecha = LocalDate.parse(campos[3].trim());
                        pagos.add(new Pago(id, idCliente, monto, fecha));
                    } catch (Exception e) {
                        System.err.println("Error en el formato de la linea de pagos: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en pagos: " + e.getMessage());
        }
        return pagos;
    }

    @Override
    public void guardarPagos(List pagos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaPagos)) {
            for (Pago p : pagos) {
                String linea = p.getId() + ";" + p.getIdCliente() + ";" + p.getMonto() + ";" + p.getFecha();
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pagos: " + e.getMessage());
        }
    }
}