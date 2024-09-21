import java.io.FileWriter;  
import java.io.IOException;  
import java.io.BufferedReader;  
import java.io.FileReader;  import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {
    private ArrayList<Peligro> peligros;
    private Scanner scan;

    public Mapa() {
        this.peligros = new ArrayList<>();
        this.scan = new Scanner(System.in);
    }

public void agregarPeligro(Peligro peligro) {
    if (peligro != null) {
        System.out.println("Ingrese el tipo de Peligro: ");
        String tipoPeligro = scan.next();
        peligro.setTipo(tipoPeligro);
        
        System.out.println("Escriba una breve descripcion del peligro: ");
        String descripcionPeligro = scan.next();
        peligro.setDescripcion(descripcionPeligro);
        
        //Determinar la longitud del peligro.
        System.out.println("Seleccione la longitud del peligro: ");
        System.out.println("1. Bajo (menos de 10 metros)");
        System.out.println("2. Medio (entre 10 y 50 metros)");
        System.out.println("3. Alto (más de 50 metros)");
        int opcion = scan.nextInt();
        switch(opcion){
            case 1:
                peligro.setLongitud("Bajo");
                break;
            case 2:
                peligro.setLongitud("Medio");
                break;
            case 3;
                peligro.setLongitud("Alto");
                break;
            default;
                System.out.println("Opción inválida. Seleccionando longitud por defecto (Bajo)");
                peligro.setLongitud("Bajo");
                break;
            }

        // determinar la latitud del peligro
        System.out.println("Seleccione la latitud del peligro: ");
        System.out.println("1. Norte");
        System.out.println("2. Sur");
        System.out.println("3. Este");
        System.out.println("4. Oeste");
        int ocpcionLat = scan.nextInt();
        switch(opcionLat){
            case 1:
                peligro.setLatitud("Norte");
                break;
            case 2:
                peligro.setLatitud("Sur");
                break;
            case 3:
                peligro.setLatitud("Este");
                break;
            case 4:
                peligro.setLatitud("Oeste");
                break;
            default;
                System.out.println("Opcion inválida. Seleccionando longitud por defecto (Norte)");
                peligro.setLatitud("Norte");
                break;
            }

        if (!peligroYaAgregado(peligro)) {
            peligros.add(peligro);
        //Actualizar lista de peligros.
        agregarPeligro(peligro);
            System.out.println("Peligro agregado: " + peligro.getTipo());
            } else {
                System.out.println("El peligro ya está registrado en esta ubicación.");
            }
        } else {
            System.out.println("El peligro no puede ser nulo.");
        }
    }

// Método para eliminar un peligro
    public void eliminarPeligro(Peligro peligro) {
        peligros.remove(peligro);
        System.out.println("Peligro eliminado: " + peligro.getTipo());
    }

// Obtener lista de peligros
    public ArrayList<Peligro> getPeligros() {
        if (peligros.isEmpty()) {
            System.out.println("No hay peligros registrados.");
        }
        return peligros;
    }
// Guardar peligros en un archivo CSV
    public void guardarPeligrosCSV(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            for (Peligro peligro : peligros) {
                writer.append(peligro.getTipo());
                writer.append(",");
                writer.append(peligro.getDescripcion());
                writer.append(",");
                writer.append(peligro.getLatitud());
                writer.append(",");
                writer.append(peligro.getLongitud());
                writer.append("\n");
            }
            System.out.println("Peligros guardados en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar los peligros en el archivo CSV: " + e.getMessage());
        }
    }

    // Cargar peligros desde un archivo CSV
    public void cargarPeligrosCSV(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                Peligro peligro = new Peligro();
                peligro.setTipo(datos[0]);
                peligro.setDescripcion(datos[1]);
                peligro.setLatitud(datos[2]);
                peligro.setLongitud(datos[3]);
                peligros.add(peligro);  // Añadir el peligro a la lista
            }
            System.out.println("Peligros cargados desde el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar los peligros desde el archivo CSV: " + e.getMessage());
        }
    }

// metodo para obtener peligros cercanos    
    public ArrayList<Peligro> getPeligrosCercanos(double latitud, double longitud, double rango) {
    ArrayList<Peligro> peligrosCercanos = new ArrayList<>();
    for (Peligro p : peligros) {
        if (distancia(p.getLatitud(), p.getLongitud(), latitud, longitud) <= rango) {
            peligrosCercanos.add(p);
        }
    }
    if (peligrosCercanos.isEmpty()) {
        System.out.println("No se encontraron peligros cercanos.");
    }
    return peligrosCercanos;
    }

    //metodo auxiliar para calcualr la distancia entre dos puntos
    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double radioTierra = 6371; // Radio de la tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
               Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierra * c; // Devuelve la distancia en km
    }

// verificar si un peligro ya ha sido agregado 
    public boolean peligroYaAgregado(Peligro peligro) {
        for (Peligro p : peligros) {
            if (p.getLatitud() == peligro.getLatitud() && p.getLongitud() == peligro.getLongitud()) {
                return true;
            }
        }
        return false;
    }
    
    // Actualización en el método agregarPeligro para evitar duplicados
    public void agregarPeligro(Peligro peligro) {
        if (!peligroYaAgregado(peligro)) {
            peligros.add(peligro);
            System.out.println("Peligro agregado: " + peligro.getTipo());
        } else {
            System.out.println("El peligro ya está registrado en esta ubicación.");
        }
    }

// mostrar todos los peligros 
    public void mostrarPeligros() {
        if (peligros.isEmpty()) {
            System.out.println("No hay peligros registrados.");
        } else {
            System.out.println("Peligros registrados:");
            for (Peligro p : peligros) {
                System.out.println("Tipo: " + p.getTipo() + ", Descripción: " + p.getDescripcion() + 
                                   ", Latitud: " + p.getLatitud() + ", Longitud: " + p.getLongitud());
            }
        }
    }

    // actualizar la informacion de un peligro 
    public void actualizarPeligro(Peligro peligro, String nuevoTipo, String nuevaDescripcion, int nuevaGravedad) {
        for (Peligro p : peligros) {
            if (p.getLatitud() == peligro.getLatitud() && p.getLongitud() == peligro.getLongitud()) {
                p.setTipo(nuevoTipo);
                p.setDescripcion(nuevaDescripcion);
                p.setGravedad(nuevaGravedad);
                System.out.println("Peligro actualizado: " + nuevoTipo);
                return;
            }
        }
        System.out.println("Peligro no encontrado.");
    }    
}
