import java.util.ArrayList;

public class Mapa {
    private ArrayList<Peligro> peligros;

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


    
}
