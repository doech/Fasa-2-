import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Peligro {
    private String avenida;
    private String calle;
    private String descripcion;
    private boolean reparado;
    private int calificacion;
    private static final int TIEMPO_APROXIMADO_REPARACION = 1; 
    private LocalDate fechaRegistro;
    private LocalDate fechaColocacion;
    private String zona;

    public Peligro(String avenida, String calle, String descripcion) {
        this.avenida = avenida;
        this.calle = calle;
        this.descripcion = descripcion;
        this.reparado = false;
        this.calificacion = 0;
        this.fechaColocacion = fechaColocacion;
        this.fehcaRegistro = LocalDate.now();
        this.fechaColocacion = null; 
        this.zona = zona;
    }

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

    public int getTiempoAproximadoReparacion() {
        return TIEMPO_APROXIMADO_REPARACION;
    }

    public LocalDate getFechaColocacion(){
        return fechaColocacion;
    }
    
    // Método para calcular el tiempo transcurrido desde la fecha de registro
    public long getTiempoTranscurridoEnHoras() {
        return ChronoUnit.DAYS.between(fechaColocacion, LocalDate.now());
    }

    // Método para enviar notificación si el peligro sigue sin reparar después del tiempo estimado
    public void enviarNotificacionSiNecesario() {
        if (!reparado && getTiempoTranscurridoDesdeColocacion() > TIEMPO_APROXIMADO_REPARACION) {
            System.out.println("ALERTA: El peligro en Avenida: " + avenida + ", Calle: " + calle +
                               " permanece sin reparar por más tiempo del estimado.");
    }

    @Override
    public String toString() {
        return "Peligro en Avenida: " + avenida + ", Calle: " + calle + ", Descripción: " + descripcion +
               ", Reparado: " + reparado + ", Calificación: " + calificacion +
               ", Fecha de Colocación: " + fechaColocacion +
               ", Tiempo Aproximado de Reparación: " + TIEMPO_APROXIMADO_REPARACION + " día(s)" +
               ", Tiempo Transcurrido desde Colocación: " + getTiempoTranscurridoDesdeColocacion() + " día(s)";
    }
}
