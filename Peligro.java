public class Peligro {
    private String avenida;
    private String calle;
    private String descripcion;
    private boolean reparado;
    private int calificacion;

    public Peligro(String avenida, String calle, String descripcion) {
        this.avenida = avenida;
        this.calle = calle;
        this.descripcion = descripcion;
        this.reparado = false;
        this.calificacion = 0;
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

    @Override
    public String toString() {
        return "Peligro en Avenida: " + avenida + ", Calle: " + calle + ", Descripción: " + descripcion +
               ", Reparado: " + reparado + ", Calificación: " + calificacion;
    }
}
