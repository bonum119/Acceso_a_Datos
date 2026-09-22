import java.util.Scanner;

public class ConsolaUI {
Scanner sc = new Scanner(System.in);
GasolineraGestor gestor = new GasolineraGestor();

    public void iniciar(){
        boolean terminar = false;

        while (!terminar){
            mostrarmenu();
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    darDeAltaCliente();
                    break;
                case "2":
                    //listarClientes();
                    break;
                case "3":
                    //buscarClientes();
                    break;
                case "4":
                   // procesarPago();
                    break;
                case "5":
                   // consultarPagos();
                    break;
                case "0":
                    terminar = true;
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private void darDeAltaCliente() {
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String matricula = leerTexto("Matrícula: ").toUpperCase();

        if (gestor.matriculaRegistrada(matricula)) {
            System.out.println("Esa matrícula ya está registrada. No se ha creado el cliente.");
            return;
        }

        Cliente cliente = gestor.altaCliente(nombre, telefono, matricula);
        System.out.println("Cliente registrado con ID " + cliente.getId() + ".");
    }

    private String leerTexto(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = sc.nextLine();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo es obligatorio.");
        }
    }

    private void mostrarmenu() {
        System.out.println("=== GESTIÓN DE GASOLINERA ===\n" +
                "1. Dar de alta un cliente\n" +
                "2. Listar clientes\n" +
                "3. Buscar clientes\n" +
                "4. Procesar un pago de repostaje\n" +
                "5. Consultar pagos\n" +
                "0. Salir\n" +
                "Opción:\n");
    }

}
