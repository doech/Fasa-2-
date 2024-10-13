import java.util.ArrayList;
import java.util.List;
/**
 * La clase Mapa gestiona los peligros reportados en el sistema.
 */
public class Mapa {
    private List<Peligro> peligros;
    /**
     * Constructor de la clase Mapa. Inicializa la lista de peligros.
     */
    public Mapa() {
        peligros = new ArrayList<>();
    }
    /**
     * Agrega un nuevo peligro a la lista de peligros.
     *
     * @param peligro El peligro que se desea agregar.
     */
    public void agregarPeligro(Peligro peligro) {
        peligros.add(peligro);
    }
    /**
     * Devuelve la lista de peligros registrados en el mapa.
     *
     * @return Lista de peligros en ubicacion específica.
     */
    public List<Peligro> obtenerPeligrosEnUbicacion(String zona, String calle, String avenida) {
        List<Peligro> peligrosEnUbicacion = new ArrayList<>();
        for (Peligro p : peligros) {
            if (p.getZona().equals(zona) && p.getCalle().equals(calle) && p.getAvenida().equals(avenida)) {
                peligrosEnUbicacion.add(p);
            }
        }
        return peligrosEnUbicacion;
    }

    // obtener peligros cercanos a una ubicación
    public List<Peligro> obtenerPeligrosCercanos(String zona, String calle, String avenida, int limite) {
        List<Peligro> peligrosCercanos = new ArrayList<>();
    
    /**
     * Guarda la lista de peligros en un archivo CSV.
     * Este método aún no ha sido implementado.
     *
     * @param archivo El nombre del archivo CSV donde se guardarán los peligros.
     */
    public void guardarPeligrosCSV(String archivo) {
        System.out,println("Guardar peligros en CSV no implementado")
    }    
}
