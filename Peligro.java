import java.time.LocalDate;

public class Peligro {
    private String avenida;
    private String calle;
    private String descripcion;
    private boolean reparado;
    private int calificacion;
    private static final int TiempoAproximadoReparacion = 1;
    private LocalDate fechaRegistro; // Fecha de registro
    private LocalDate fechaColocacion;
    private String zona;

    // Constructor
    public Peligro(String avenida, String calle, String descripcion, String zona) {
        this.avenida = avenida;
        this.calle = calle;
        this.descripcion = descripcion;
        this.reparado = false;
        this.calificacion = 0;
        this.fechaRegistro = LocalDate.now(); // Registrar la fecha actual al crear un nuevo peligro
        this.fechaColocacion = null;
        this.zona = zona;
    }

    // Métodos getters y setters

    public String getZona() {
        return zona;
    }

    public String getAvenida() {
        return avenida;
    }

    public String getCalle() {
        return calle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isReparado() {
        return reparado;
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDate getFechaColocacion() {
        return fechaColocacion;
    }

    public int getTiempoAproximadoReparacion(){
        return TiempoAproximadoReparacion;
    }

    @Override
    public String toString() {
        return "Peligro en Avenida: " + avenida + ", Calle: " + calle + ", Descripción: " + descripcion +
               ", Reparado: " + (reparado ? "Sí" : "No") + ", Calificación: " + calificacion +
               ", Fecha de Registro: " + fechaRegistro +
               ", Tiempo Aproximado de Reparación: " + TiempoAproximadoReparacion + " día(s)";
    }

}
