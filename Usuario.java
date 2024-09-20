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
        if (puntos > 0){
            this.puntos += puntos;
        } else{
            System.out.println("No se pueden añadir puntos negativos");
        }
        if (reportarPeligro.get(reportarPeligro) != null) {
            int puntos = reportarPeligro.get(reportarPeligro);
            reportarPeligro.put(agregarPuntos, puntos + 10); //agrega 10 puntos al usuario por reportar el peligro.
        } else {
            reportarPeligro.put(agregarPuntos, 10); //si el usuario no tiene puntos, el valor de "puntos" es 10.
        }
    }

    public void reportarPeligro(Mapa mapa, Peligro peligro) {
        mapa.agregarPeligro(peligro);
        this.agregarPuntos(10); // por ejemplo, 10 puntos por cada reporte
    }

    // Validación de email y actualización
    public boolean esEmailValido(String email) {
    return email.contains("@") && email.contains(".");
    }

    public void actualizarEmail(String nuevoEmail) {
    if (esEmailValido(nuevoEmail)) {
        this.email = nuevoEmail;
        System.out.println("Email actualizado a: " + nuevoEmail);
        } else {
        System.out.println("El email proporcionado no es válido.");
    }
    }

}
