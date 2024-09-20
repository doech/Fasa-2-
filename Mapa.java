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

    public void agregarPeligro(Peligro peligro) {
    if (peligro != null) {
        System.out.println("Ingrese el tipo de Peligro: ");
        String tipoPeligro = scan.next();
        System.out.println("Escriba una breve descripcion del peligro: ");
        String descripcionPeligro = scan.next();
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
        //Actualizar lista de peligros.
        agregarPeligro(peligro);
    }
        
        
        peligros.add(peligro);
        System.out.println("Peligro agregado: " + peligro.getTipo());
    } else {
        System.out.println("El peligro no puede ser nulo.");
    }
    }

    public void eliminarPeligro(Peligro peligro) {
        peligros.remove(peligro);
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



    
}
