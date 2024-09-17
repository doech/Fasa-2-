public class Peligro {
    private String tipo; // bache, calle sin luz, derrumbe, etc.
    private String descripcion;
    private double latitud;
    private double longitud;
    private boolean reparado;
    private int gravedad;
    private int calificacion;

    public Peligro(String tipo, String descripcion, double latitud, double longitud) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.reparado = false;
        this.gravedad = gravedad;
        this.calificacion = 0;
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

    public int getGravedad() {
        return gravedad;
    }

    public boolean isReparado() {
        return reparado;
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
    }

    public int getCalificacion() {
        return calificacion
    }

    public void setCalificacion(int calificacion) {
        if (reparado) {
            this.calificacion = calificacion;
        } else {
            System.out.println("El peligro aún no ha sido reparado");
        }
    }

    private boolean reparado;

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
        if (reparado) {
            System.out.println("Peligro marcado como reparado.");
        } else {
            System.out.println("Peligro aún sin reparar.");
    }
}

    private int calificacion;

    public int getCalificacion() {
        return calificacion;
}


} 
