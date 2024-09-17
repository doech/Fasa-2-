import java.util.ArrayList;
import java.util.Scanner;

public class Mapa {
    private ArrayList<Peligro> peligros;
    public Scanner scan;

    public Mapa() {
        this.peligros = new ArrayList<>();
    }

    public void agregarPeligro(Peligro peligro) {
        peligros.add(peligro);
    }

    public ArrayList<Peligro> getPeligros() {
        return peligros;
    }

    public ArrayList<Peligro> getPeligrosCercanos(double latitud, double longitud, double rango) {
        ArrayList<Peligro> peligrosCercanos = new ArrayList<>();
        for (Peligro p : peligros) {
            if (distancia(p.getLatitud(), p.getLongitud(), latitud, longitud) <= rango) {
                peligrosCercanos.add(p);
            }
        }
        return peligrosCercanos;
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        // Método para calcular la distancia entre dos puntos geográficos
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }

    public void agregarPeligro(Peligro peligro) {
    if (peligro != null) {
        System.out.println("Ingrese el tipo de Peligro: ");
        String tipoPeligro = scan.next();
        System.out.println("Escriba una breve descripcion del peligro: ");
        String descripcionPeligro = scan.next();
        //Agregar latitud, longitud y gravedad con opciones predeterminadas.
        
        peligros.add(peligro);
        System.out.println("Peligro agregado: " + peligro.getTipo());
    } else {
        System.out.println("El peligro no puede ser nulo.");
    }
    }

    public ArrayList<Peligro> getPeligros() {
    if (peligros.isEmpty()) {
        System.out.println("No hay peligros registrados.");
    }
    return peligros;
    }

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




    
}
