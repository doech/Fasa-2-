/**
 * La clase Peligro representa un peligro reportado en el sistema.
 * Los peligros pueden ser baches, calles sin luz, derrumbes, etc.
 */
public class Peligro {
    private String tipo; // bache, calle sin luz, derrumbe, etc.
    private String descripcion;
    private double latitud;
    private double longitud;
    private boolean reparado;
    private int gravedad;
    private int calificacion;
    private String carril;
      /**
     * Constructor de la clase Peligro.
     * 
     * @param tipo El tipo de peligro.
     * @param descripcion Breve descripción del peligro.
     * @param latitud Latitud de la ubicación del peligro.
     * @param longitud Longitud de la ubicación del peligro.
     * @param gravedad Nivel de gravedad del peligro (1 a 5).
     * @param carril Carril afectado (izquierdo o derecho).
     */
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
     * Obtiene la latitud de la ubicación del peligro.
     * 
     * @return La latitud del peligro.
     */
    public double getLatitud() {
        return latitud;
    }
    /**
     * Establece la latitud de la ubicación del peligro.
     * 
     * @param latitud La latitud del peligro.
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    /**
     * Obtiene la longitud de la ubicación del peligro.
     * 
     * @return La longitud del peligro.
     */
    public double getLongitud() {
        return longitud;
    }
     /**
     * Establece la longitud de la ubicación del peligro.
     * 
     * @param longitud La longitud del peligro.
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
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
                this.calificacion = 0;
        }
    }
     /**
     * Obtiene la calificación de la reparación del peligro.
     * 
     * @return La calificación (entre 0 y 5).
     */
    public int getCalificacion() {
        return calificacion
    }
    /**
     * Establece la calificación para la reparación del peligro.
     * Solo puede establecerse si el peligro ha sido reparado.
     * 
     * @param calificacion La calificación (entre 0 y 5).
     */
    public void setCalificacion(int calificacion) {
        if (reparado) {
           if (calificacion >= 0 && calificacion <= 5){
               this.calificacion = calificacion;
               System.out.println("Calificación registrada: " + calificacion);
           } else{
               System.out.println("La calificación debe estar entre 0 y 5");
           }
        } else{
            System.out.println("El peligro aún no ha sido reparado");
        }
    }
