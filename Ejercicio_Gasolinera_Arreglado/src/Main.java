public class Main {

    public static void main(String[] args) {
        ClienteRepositorio clienteRepositorio = new MemoriaRepositorio();
        PagoRepositorio pagoRepositorio = new MemoriaPagoRepositorio();
        GasolineraApp app = new GasolineraApp(clienteRepositorio, pagoRepositorio);
        ConsolaUI consolaUI = new ConsolaUI(app);
        consolaUI.iniciar();
    }
}
