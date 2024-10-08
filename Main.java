import java.util.Scanner;

public class Main {
    /**
     * @param args
     * la clase Main mostrará las opciones al usuario de lo que puede hacer en el programa usando switch
     */
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

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el tipo de peligro:");
                    String tipoPeligro = scanner.nextLine();

                    System.out.println("Ingrese la descripción del peligro:");
                    String descripcionPeligro = scanner.nextLine();

                    System.out.println("Ingrese la latitud:");
                    double latitud = scanner.nextDouble();

                    System.out.println("Ingrese la longitud:");
                    double longitud = scanner.nextDouble();

                    System.out.println("Ingrese la gravedad (1-5):");
                    int gravedad = scanner.nextInt();

                    scanner.nextLine(); // Limpiar el buffer
                    System.out.println("Ingrese el carril (izquierdo/derecho):");
                    String carril = scanner.nextLine();

                    // Crear y agregar el peligro
                    Peligro peligro = new Peligro(tipoPeligro, descripcionPeligro, latitud, longitud, gravedad, carril);
                    mapa.agregarPeligro(peligro);

                    usuario.agregarPuntos(10); // Dar puntos al usuario por reportar
                    System.out.println("Peligro reportado exitosamente.");
                    break;

                    case 2:
                    System.out.println("Peligros reportados:");
                    for (Peligro p : mapa.getPeligros()) {
                        System.out.println(p.getTipo() + " - " + p.getDescripcion());
                    }
                    break; case 3:
                    System.out.println("Guardando peligros en CSV...");
                    mapa.guardarPeligrosCSV("peligros.csv");
                    System.out.println("Peligros guardados exitosamente.");
                    break;

                case 4:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        scanner.close();
    }
} 
