import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private List<Peligro> peligros;

    public Mapa() {
        peligros = new ArrayList<>();
    }

    public List<Peligro> getPeligrosPorZona(String zona) {
    List<Peligro> peligrosFiltrados = new ArrayList<>();
    for (Peligro peligro : peligros) {
        if (peligro.getZona().equalsIgnoreCase(zona)) {
            peligrosFiltrados.add(peligro);
        }
    }
    return peligrosFiltrados;
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

    // Método para eliminar un peligro con su ubicación (avenida y calle)
    public void eliminarPeligro(String avenida, String calle) {
        for (int i = 0; i < peligros.size(); i++) {
            Peligro peligro = peligros.get(i);
            if (peligro.getAvenida().equals(avenida) && peligro.getCalle().equals(calle)) {
                peligros.remove(i);
                return; 
            }
        }
        throw new IllegalArgumentException("No se encontró ningún peligro en la avenida " + avenida + " y la calle " + calle);
    }

    // Método para la lista de peligros que pueden necesitar ser repintados
    public List<Peligro> obtenerPeligrosParaRepintado() {
        return new ArrayList<>(peligros);
    }

}
