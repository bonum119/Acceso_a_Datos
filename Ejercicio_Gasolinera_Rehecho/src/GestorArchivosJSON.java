
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        for(Map<String, String> objeto : leerObjetos(rutaClientes)){
            try {
                int id = Integer.parseInt(campo(objeto, "id"));
                clientes.add(new Cliente(id, campo (objeto, "nombre"), campo(objeto, "telefono"), campo(objeto, "matricula")));
            }catch (IOException e){
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public boolean guardarClientes(List<Cliente> clientes) {
        List<String> objetos = new ArrayList<>();
        for (Cliente c : clientes){
            objetos.add("\n\t{\"id\": " + c.getId() + ", \"nombre\": " + c.getNombre() + ", \"telefono\": " + c.getTelefono() + ", \"matricula\": " + c.getMatricula() + "}");
        }
        return escribir(objetos);
    }

    //PAGOS--------------------------------------------
    @Override
    public List leerPagos() {
        List pagos = new ArrayList<>();
        if (Files.notExists(rutaPagos)) return pagos;

        try (BufferedReader lector = Files.newBufferedReader(rutaPagos)) {

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
                String linea = "\"id\" " + p.getId() + ",\"idCliente\" " + p.getIdCliente() + ",\"fecha\" " + p.getFecha()
                        + ",\"importe\" " + p.getImporte() + ",\"litros\" " + p.getLitros() + ",\"combustible\" " + p.getCombustible();
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pagos: " + e.getMessage());
        }
    }

    //ESCRIBIR--------------------------------------------

    private boolean escribir (List<String> clientes){
        try (BufferedWriter escritor = Files.newBufferedWriter(rutaClientes)) {
            String linea = "";
            escritor.write(linea);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //LEER------------------------------------------------
    private Map<String, String>[] leerObjetos(Path rutaClientes) {
        if(Files.notExists(rutaClientes)) return new ArrayList<>();
    }
}