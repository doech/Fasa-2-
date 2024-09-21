import java.util.ArrayList;
/**
 * La clase Mapa gestiona los peligros reportados en el sistema.
 */
public class Mapa {
    private ArrayList<Peligro> peligros;
    /**
     * Constructor de la clase Mapa. Inicializa la lista de peligros.
     */
    public Mapa() {
        this.peligros = new ArrayList<>();
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
     * @return Una lista de objetos Peligro.
     */
public ArrayList<Peligro> getPeligros() {
    return peligros;
}
    
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
