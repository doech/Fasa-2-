import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
/**
 * La clase Usuario representa a un usuario en el sistema, con nombre, email y un sistema de recompensas basado en puntos.
 */
public class Usuario {
    private String nombre;
    private String email;
    private int puntos; // para el sistema de recompensas
    private HashMap<String, Integer> reportarPeligro; 
 /**
     * Crea un nuevo usuario con el nombre y el email especificados.
     *
     * @param nombre El nombre del usuario.
     * @param email El email del usuario.
     */
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.puntos = 0;
        this.reportarPeligro = new HashMap<>();
    }
 /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * 
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 /**
     * Obtiene el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }
     /**
     * 
     * Establece el email del usuario.
     *
     * @param email El email a establecer.
     */
    public void setEmail(String email) {
        if (esEmailValido(email)) {
            this.email = email;
        } else {
            System.out.println("El email proporcionado no es válido.");
        }
    }

/**
     * Obtiene la cantidad de puntos del usuario.
     *
     * @return La cantidad de puntos.
     */
    public int getPuntos() {
        return puntos;
    }
/**
     * Agrega puntos al usuario. Si se reporta un peligro, se le agregan los puntos correspondientes.
     *
     * @param puntos Los puntos a agregar.
     */
    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntos += puntos;
        } else {
            System.out.println("No se pueden añadir puntos negativos.");
        }
    }

 /**
     * Reporta un peligro y otorga puntos al usuario.
     *
     * @param mapa   El mapa donde se agregará el peligro.
     * @param peligro El peligro reportado.
     */
    public void reportarPeligro(Mapa mapa, Peligro peligro) {
        mapa.agregarPeligro(peligro);
        String tipoPeligro = peligro.getTipo();

        //Implementación de getOrDefault para manejo de puntos ya existentes.
        int puntosParaAgregar = reportarPeligro.getOrDefault(tipoPeligro, 0) +10;
        reportarPeligro.put(tipoPeligro, puntosParaAgregar);
        
        //Verificacion de si el peligro ha sido reportado anteriormente.
        if (reportarPeligro.containsKey(tipoPeligro)) {
            int puntosPrevios = reportarPeligro.get(tipoPeligro);
            reportarPeligro.put(tipoPeligro, puntosPrevios + 10);
        } else {
            reportarPeligro.put(tipoPeligro, 10); 
        }
        //agregar puntos generales al usuario.
        this.agregarPuntos(10);
        // mensaje para el usuario.
        System.out.println("Peligro reportado: " + tipoPeligro + "Ganaste +10 puntos!");
    }
   /**
     * Valida si el email proporcionado es válido.
     *
     * @param email El email a validar.
     * @return true si el email es válido, false de lo contrario.
     */

    public boolean esEmailValido(String email) {
        return email.contains("@") && email.contains(".");
    }
    /**
     * Actualiza el email del usuario si es válido.
     *
     * @param nuevoEmail El nuevo email a asignar.
     */
    public void actualizarEmail(String nuevoEmail) {
        if (esEmailValido(nuevoEmail)) {
            this.email = nuevoEmail;
            System.out.println("Email actualizado a: " + nuevoEmail);
        } else {
            System.out.println("El email proporcionado no es válido.");
        }
    }

    // Sistema de niveles basado en puntos
    public String getNivel() {
        if (puntos < 100) {
            return "Principiante";
        } else if (puntos < 500) {
            return "Intermedio";
        } else {
            return "Experto";
        }
    }
      /**
     * Muestra los detalles del usuario, incluyendo su nombre, email, puntos y nivel.
     */
    public void mostrarDetallesUsuario() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Puntos: " + puntos);
        System.out.println("Nivel: " + getNivel());
    }
}
// Guardar datos de usuario en un archivo CSV simple
 public void guardarDatosCSV(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.append(nombre);
            writer.append(",");
            writer.append(email);
            writer.append(",");
            writer.append(Integer.toString(puntos));
            writer.append("\n");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos en CSV: " + e.getMessage());
        }
    }
}
