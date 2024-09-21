public class Peligro {
    private String tipo; // bache, calle sin luz, derrumbe, etc.
    private String descripcion;
    private double latitud;
    private double longitud;
    private boolean reparado;
    private int gravedad;
    private int calificacion;
    private String carril;

    public Peligro(String tipo, String descripcion, double latitud, double longitud, int gravedad, String carril) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.reparado = false;
        setGravedad(gravedad);
        this.calificacion = 0;
        setCarril(carril);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setGravedad(int gravedad) {
    if (gravedad < 1 || gravedad > 5) {
        throw new IllegalArgumentException("La gravedad debe estar entre 1 y 5.");
    }
    this.gravedad = gravedad;
    }

    public String getCarril() {
        return carril;
    }

    public void setCarril(String carril) {
        if (carril.equalsIgnoreCase("izquierdo") || carril.equalsIgnoreCase("derecho")) {
            this.carril = carril;
        } else {
            throw new IllegalArgumentException("El carril debe ser 'izquierdo' o 'derecho'.");
        }
    }

    public boolean isReparado() {
        return reparado;
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
        this.reparado = reparado;
        if (!reparado) {
                this.calificacion = 0;
        }
    }

    public int getCalificacion() {
        return calificacion
    }

    public void setCalificacion(int calificacion) {
        if (reparado) {
           if (calificacion >= 1 && calificacion <= 5){
               this.calificacion = calificacion;
           } else{
               System.out.println("La calificación debe estar entre 1 y 5");
           }
        } else{
            System.out.println("El peligro aún no ha sido reparado");
        }
    }

    public void setReparado(boolean reparado) {
        this.reparado = reparado;
        if (reparado) {
            System.out.println("Peligro marcado como reparado.");
        } else {
            System.out.println("Peligro aún sin reparar.");
    }
}

    public int getCalificacion() {
        return calificacion;
}

    public void setCalificacion(int calificacion) {
    if (reparado) {
        if (calificacion >= 0 && calificacion <= 5) {
            this.calificacion = calificacion;
            System.out.println("Calificación registrada: " + calificacion);
        } else {
            System.out.println("La calificación debe estar entre 0 y 5.");
        }
    } else {
        System.out.println("El peligro aún no ha sido reparado.");
    }
}



} 
