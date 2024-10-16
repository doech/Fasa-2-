import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String avenida;
    private String calle;
    private int puntos;

    public Usuario(String nombre, String avenida, String calle) {
        this.nombre = nombre;
        this.avenida = avenida;
        this.calle = calle;
        this.puntos = 0; // Inicialmente 0 puntos
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvenida() {
        return avenida;
    }

    public String getCalle() {
        return calle;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", avenida='" + avenida + '\'' +
                ", calle='" + calle + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
