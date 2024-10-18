import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private List<Peligro> peligros;

    public Mapa() {
        peligros = new ArrayList<>();
    }

    // Método para registrar un nuevo peligro
    public void registrarPeligro(Peligro peligro) {
        peligros.add(peligro);
    }

    // Obtener la lista de peligros
    public List<Peligro> getPeligros() {
        return peligros;
    }

    // Método para marcar un peligro como reparado
    public void marcarPeligroReparado(String avenida, String calle) {
        for (Peligro peligro : peligros) {
            if (peligro.getAvenida().equals(avenida) && peligro.getCalle().equals(calle)) {
                peligro.setReparado(true);
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún peligro en la avenida " + avenida + " y la calle " + calle);
    }

    // Método para calificar la reparación de un peligro
    public void calificarPeligro(String avenida, String calle, int calificacion) {
        for (Peligro peligro : peligros) {
            if (peligro.getAvenida().equals(avenida) && peligro.getCalle().equals(calle)) {
                peligro.setCalificacion(calificacion);
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún peligro en la avenida " + avenida + " y la calle " + calle);
    }
}
