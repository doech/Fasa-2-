import java.io.fileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Usuario {
    private String nombre;
    private String email;
    private int puntos; // para el sistema de recompensas
    private hashMAP<String, Integer> reportarPeligro; 

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.puntos = 0;
        this.reportarPeligro = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int puntos) {
        if (puntos > 0){
            this.puntos += puntos;
        } else{
            System.out.println("No se pueden añadir puntos negativos");
        }
    }

    public void reportarPeligro(Mapa mapa, Peligro peligro) {
        mapa.agregarPeligro(peligro);
        String tipoPeligro = peligro.getTipo();

        // verficar si el peligro ya ha sido reportado 
        if (reportarPeligro.containskey(tipoPeligro)) {
            int puntosPrevios = reportarPeligro.get(tipoPeligro);
            reportarPeligro.put(tipoPeligro, puntosPrevios + 10);
        } else {
            reportarPeligro.put(tipoPeligro, 10); 
        }
        //agregar puntos generales al usuario
        this.agregarPuntos(10); // por ejemplo, 10 puntos por cada reporte
    }

    // Validación de email y actualización
    public boolean esEmailValido(String email) {
        return email.contains("@") && email.contains(".");
    }

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
