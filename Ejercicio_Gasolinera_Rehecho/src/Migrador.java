import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Migrador {

    public static void migrar() {
        if (Files.exists(Path.of("datosjson", "clientes.json"))
                || Files.exists(Path.of("datosjson", "pagos.json"))) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("¿Hay ficheros CSV que migrar? (s/n): ");
        if (!sc.nextLine().equalsIgnoreCase("s")) {
            return;
        }

        GestorArchivos csv = new GestorArchivosCSV();
        GestorArchivos json = new GestorArchivosJSON();

        List<Cliente> clientes = csv.leerClientes();
        List<Pago> pagos = csv.leerPagos();

        json.guardarClientes(clientes);
        json.guardarPagos(pagos);

        System.out.println("Clientes migrados: " + clientes.size());
        System.out.println("Pagos migrados: " + pagos.size());
        System.out.println("Migración completada");
    }
}