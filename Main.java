public class Main {
    public static void main(String[] args){
        Mapa mapa = new Mapa();
        Usuario usuario = new Usuario("Juan", "juan@example.com"); // Cambio aquí

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Reportar un peligro");
            System.out.println("2. Mostrar peligros reportados");
            System.out.println("3. Guardar peligros en CSV");
            System.out.println("4. Salir");

        
