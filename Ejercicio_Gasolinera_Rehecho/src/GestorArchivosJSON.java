
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestorArchivosJSON implements GestorArchivos {

    private final Path rutaClientes = Path.of("datosjson", "clientes.json");
    private final Path rutaPagos = Path.of("datosjson", "pagos.json");

    public GestorArchivosJSON() {
        asegurarRuta();
    }

    private void asegurarRuta() {
        try {
            Path directorio = Path.of("datosjson");
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

    //CLIENTES--------------------------------------

    @Override
    public List leerClientes() {
        List clientes = new ArrayList<>();
        if (Files.notExists(rutaClientes)) return clientes;

        try (BufferedReader lector = Files.newBufferedReader(rutaClientes)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.equals("\t[")||linea.equals("\t]")||linea.equals("{")||linea.equals("}")||linea.isBlank()) continue;

                linea = linea.replace("{", "").replace("\"", "");
                linea = linea.replace("}", "").replace("\"", "");
                String[] campos = linea.split(",");
                if (campos.length != 4) {
                    System.err.println("Error al leer cliente: " + linea);
                }
                try {
                    int id = Integer.parseInt(campos[0].split(":")[1].trim());
                    String nombre = campos[1].split(":")[1].trim();
                    String telefono = campos[2].split(":")[1].trim();
                    String matricula = campos[3].split(":")[1].trim();
                    clientes.add(new Cliente(id, nombre, telefono, matricula));
                } catch (NumberFormatException e) {
                    System.err.println("Error: " + linea);
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
            escritor.write("{\n");
            escritor.write("\t[\n");
            for (Cliente c : clientes) {
                String linea = "\t  {\"id\": " + c.getId() + ", \"nombre\": \"" + c.getNombre() + "\", \"telefono\": \""
                        + c.getTelefono() + "\", \"matricula\": \"" + c.getMatricula() + "\"}";
                escritor.write(linea);
                escritor.newLine();
            }
            escritor.write("\t]\n");
            escritor.write("}");
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    //PAGOS--------------------------------------------
    @Override
    public List leerPagos() {
        List pagos = new ArrayList<>();
        if (Files.notExists(rutaPagos)) return pagos;

        try (BufferedReader lector = Files.newBufferedReader(rutaPagos)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.equals("\t[")||linea.equals("\t]")||linea.equals("{")||linea.equals("}")||linea.isBlank()) continue;

                linea = linea.replace("{", "").replace("\"", "");
                linea = linea.replace("}", "").replace("\"", "");
                String[] campos = linea.split(",");
                if (campos.length != 4) {
                    System.err.println("Error al leer cliente: " + linea);
                }
                try {
                    int id = Integer.parseInt(campos[0].split(":")[1].trim());
                    String idCliente = campos[1].trim();
                    LocalDate fecha = LocalDate.parse(campos[2].split(":")[1].trim());
                    double importe = Double.parseDouble(campos[3].split(":")[1].trim());
                    double litros = Double.parseDouble(campos[4].split(":")[1].trim());
                    String combustible = campos[5].split(":")[1].trim();
                    pagos.add(new Pago(id, idCliente, fecha, importe, litros, combustible));
                } catch (NumberFormatException e) {
                    System.err.println("Error: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en clientes: " + e.getMessage());
            System.exit(1);
        }
        return pagos;
    }

    @Override
    public void guardarPagos(List<Pago> pagos) {
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaClientes)) {
            escritor.write("{\n");
            escritor.write("\t[\n");
            for (Pago p : pagos) {
                String linea = "\t  {\"id\": " + p.getId() + ", \"idCliente\": \"" + p.getIdCliente() + "\", \"fecha\": \""
                        + p.getFecha() + "\", \"importe\": \"" + p.getImporte() + "\", \"litros\": \"" + p.getLitros() + "\", \"combustible\": \"" + p.getCombustible() + "\"}";
                escritor.write(linea);
                escritor.newLine();
            }
            escritor.write("\t]\n");
            escritor.write("}");
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

}