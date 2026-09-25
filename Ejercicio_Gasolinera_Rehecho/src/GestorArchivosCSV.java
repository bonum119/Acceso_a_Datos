import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
            System.out.println("Almacenamiento en: " + directorio.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al crear la estructura de archivos: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public List<Cliente> leerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        if (Files.notExists(rutaClientes)) return clientes;

        try (BufferedReader lector = Files.newBufferedReader(rutaClientes)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = dividir(linea);
                if (campos.length != 4) {
                    System.err.println("Registro de cliente ilegible: " + linea);
                    System.exit(1);
                }
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = desescapar(campos[1]);
                    String telefono = desescapar(campos[2]);
                    String matricula = desescapar(campos[3]);
                    clientes.add(new Cliente(id, nombre, telefono, matricula));
                } catch (NumberFormatException e) {
                    System.err.println("Registro de cliente ilegible: " + linea);
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en clientes: " + e.getMessage());
            System.exit(1);
        }
        return clientes;
    }

    @Override
    public void guardarClientes(List<Cliente> clientes) {
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaClientes)) {
            for (Cliente c : clientes) {
                String linea = c.getId() + "," + escapar(c.getNombre()) + ","
                        + escapar(c.getTelefono()) + "," + escapar(c.getMatricula());
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    @Override
    public List<Pago> leerPagos() {
        List<Pago> pagos = new ArrayList<>();
        if (Files.notExists(rutaPagos)) return pagos;

        try (BufferedReader lector = Files.newBufferedReader(rutaPagos)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = dividir(linea);
                if (campos.length != 6) {
                    System.err.println("Registro de pago ilegible: " + linea);
                    System.exit(1);
                }
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String idCliente = desescapar(campos[1]);
                    LocalDate fecha = LocalDate.parse(campos[2].trim());
                    double importe = Double.parseDouble(campos[3].trim());
                    double litros = Double.parseDouble(campos[4].trim());
                    String combustible = desescapar(campos[5]);
                    pagos.add(new Pago(id, idCliente, fecha, importe, litros, combustible));
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Registro de pago ilegible: " + linea);
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en pagos: " + e.getMessage());
            System.exit(1);
        }
        return pagos;
    }

    @Override
    public void guardarPagos(List<Pago> pagos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaPagos)) {
            for (Pago p : pagos) {
                String linea = p.getId() + "," + escapar(p.getIdCliente()) + "," + p.getFecha()
                        + "," + p.getImporte() + "," + p.getLitros() + "," + escapar(p.getCombustible());
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pagos: " + e.getMessage());
        }
    }

    private String escapar(String texto) {
        return texto.replace("\\", "\\\\").replace(";", "\\;");
    }

    private String desescapar(String texto) {
        return texto.replace("\\;", ";").replace("\\\\", "\\");
    }

    private String[] dividir(String linea) {
        return linea.split("(?<!\\\\);", -1);
    }
}