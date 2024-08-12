public class Usuario {
    private String nombre;
    private String email;
    private int puntos; // para el sistema de recompensas

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.puntos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void reportarPeligro(Mapa mapa, Peligro peligro) {
        mapa.agregarPeligro(peligro);
        this.agregarPuntos(10); // por ejemplo, 10 puntos por cada reporte
    }
}
