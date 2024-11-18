import java.io.*;
import java.util.*;

public class Calles {
    private Map<String, List<String>> callesPorZona; // Mapa para almacenar las calles organizadas por zona

    public Calles() {
        callesPorZona = new HashMap<>(); // Inicializar el mapa
    }

    // Método para cargar calles desde un archivo CSV
    public void cargarCallesDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Asumimos que cada línea del archivo tiene una calle
                if (datos.length == 1) { // Validamos que sea una línea válida
                    String calle = datos[0].trim(); // Remover espacios extra
                    callesPorZona.computeIfAbsent("General", k -> new ArrayList<>()).add(calle); // Agregar la calle al mapa
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Mostrar el error en caso de fallo al cargar el archivo
        }
    }

    // Método para validar si una calle existe
    public boolean validarCalle(String calle) {
        List<String> todasLasCalles = callesPorZona.getOrDefault("General", new ArrayList<>()); // Obtener las calles cargadas
        return todasLasCalles.contains(calle.trim()); // Validar si la calle ingresada está en la lista
    }
}
