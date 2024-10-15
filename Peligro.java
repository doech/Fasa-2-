import javax.swing.JOptionPane;

/**
 * La clase Peligro representa un peligro reportado en el sistema.
 * Los peligros pueden ser baches, calles sin luz, derrumbes, etc.
 */
public class Peligro {
    private String tipo; // bache, calle sin luz, derrumbe, etc.
    private String descripcion;
    private String carril;
    private String zona;
    private String calle;
    private String avenida;
    private boolean reparado;
    private int gravedad;
    private int calificacionReparacion;

    /**
     * Constructor de la clase Peligro.
     * 
     * @param tipo El tipo de peligro.
     * @param descripcion Breve descripción del peligro.
     * @param zona Zona donde está ubicado el peligro.
     * @param calle Calle de la ubicación del peligro.
     * @param avenida Avenida de la ubicación del peligro.
     * @param gravedad Nivel de gravedad del peligro (1 a 5).
     * @param carril Carril afectado (izquierdo o derecho).
     */
    public Peligro(String tipo, String descripcion, String zona, String calle, String avenida, int gravedad, String carril) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.zona = zona;
        this.calle = calle;
        this.avenida = avenida;
        this.reparado = false;
        this.gravedad = gravedad;
        this.calificacionReparacion = 0;
        setCarril(carril);  // Validación del carril a través del setter
    }

    /**
     * Obtiene el tipo de peligro.
     * 
     * @return El tipo de peligro.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de peligro.
     * 
     * @param tipo El tipo de peligro.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción del peligro.
     * 
     * @return La descripción del peligro.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una nueva descripción para el peligro.
     * 
     * @param descripcion La nueva descripción del peligro.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la zona donde está ubicado el peligro.
     * 
     * @return La zona del peligro.
     */
    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAvenida() {
        return avenida;
    }

    public void setAvenida(String avenida) {
        this.avenida = avenida;
    }

    /**
     * Verifica si el peligro ha sido reparado.
     * 
     * @return true si el peligro ha sido reparado, false en caso contrario.
     */
    public boolean isReparado() {
        return reparado;
    }

    /**
     * Establece el estado de reparación del peligro.
     * Si el peligro no está reparado, la calificación se restablece a 0.
     * 
     * @param reparado true si el peligro ha sido reparado, false en caso contrario.
     */
    public void setReparado(boolean reparado) {
        this.reparado = reparado;
        if (!reparado) {
            this.calificacionReparacion = 0;
        }
    }

    /**
     * Obtiene la gravedad del peligro.
     * 
     * @return El nivel de gravedad (entre 1 y 5).
     */
    public int getGravedad() {
        return gravedad;
    }

    /**
     * Establece la gravedad del peligro.
     * 
     * @param gravedad El nivel de gravedad (entre 1 y 5).
     * @throws IllegalArgumentException Si la gravedad no está en el rango permitido.
     */
    public void setGravedad(int gravedad) {
        if (gravedad < 1 || gravedad > 5) {
            throw new IllegalArgumentException("La gravedad debe estar entre 1 y 5.");
        }
        this.gravedad = gravedad;
    }

    /**
     * Obtiene el carril afectado por el peligro.
     * 
     * @return El carril afectado (izquierdo o derecho).
     */
    public String getCarril() {
        return carril;
    }

    /**
     * Establece el carril afectado por el peligro.
     * 
     * @param carril El carril afectado (debe ser 'izquierdo' o 'derecho').
     * @throws IllegalArgumentException Si el carril no es válido.
     */
    public void setCarril(String carril) {
        if (carril.equalsIgnoreCase("izquierdo") || carril.equalsIgnoreCase("derecho")) {
            this.carril = carril;
        } else {
            throw new IllegalArgumentException("El carril debe ser 'izquierdo' o 'derecho'.");
        }
    }

    /**
     * Obtiene la calificación de la reparación del peligro.
     * 
     * @return La calificación (entre 0 y 5).
     */
    public int getCalificacionReparacion() {
        return calificacionReparacion;
    }

    /**
     * Establece la calificación para la reparación del peligro.
     * Solo puede establecerse si el peligro ha sido reparado.
     * 
     * @param calificacion La calificación (entre 0 y 5).
     */
    public void setCalificacionReparacion(int calificacion) {
        if (reparado) {
            if (calificacion >= 0 && calificacion <= 5) {
                this.calificacionReparacion = calificacion;
                JOptionPane.showMessageDialog(null, "Calificación registrada: " + calificacion);
            } else {
                JOptionPane.showMessageDialog(null, "La calificación debe estar entre 0 y 5", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El peligro aún no ha sido reparado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String toString() {
        return "Peligro: " + tipo + ", Descripción: " + descripcion +
               ", Ubicación: Zona " + zona + ", Calle " + calle + ", Avenida " + avenida +
               ", Gravedad: " + gravedad + ", Carril: " + carril +
               ", Reparado: " + (reparado ? "Sí" : "No") + 
               ", Calificación de reparación: " + calificacionReparacion;
    }
}


    
