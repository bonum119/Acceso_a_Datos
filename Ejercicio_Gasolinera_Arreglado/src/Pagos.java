import java.time.LocalDate;

public class Pagos {

    private final int id;
    private final int idCliente;
    private final LocalDate fecha;
    private final double importe;
    private final double litros;
    private final String combustible;

    public Pagos(int id, int idCliente, LocalDate fecha, double importe, double litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getImporte() {
        return importe;
    }

    public double getLitros() {
        return litros;
    }

    public String getCombustible() {
        return combustible;
    }
}
