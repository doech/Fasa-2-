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
    
// CSV
    public void guardarPeligrosCSV(String archivo) {
        System.out,println("Guardar peligros en CSV no implementado")
    }    
}
