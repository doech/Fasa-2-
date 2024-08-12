public class Peligro {
    private String tipo; // bache, calle sin luz, derrumbe, etc.
    private String descripcion;
    private double latitud;
    private double longitud;
    private boolean reparado;

    public Peligro(String tipo, String descripcion, double latitud, double longitud) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.reparado = false;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public boolean isReparado() {
        return reparado;
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
    }
}
