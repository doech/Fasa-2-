import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Mapa mapa = new Mapa();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\nBienvenido a la Aplicación de Reporte de Peligros");
            System.out.println("1. Reportar un peligro");
            System.out.println("2. Detectar si está cerca de algún peligro");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    reportarPeligro(mapa, scanner);
                    break;
                case 2:
                    detectarPeligroCercano(mapa, scanner);
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Gracias por usar la aplicación.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        scanner.close();
    }

    private static void reportarPeligro(Mapa mapa, Scanner scanner) {
        System.out.print("Ingrese el tipo de peligro (Bache, Calle sin luz, Derrumbe, etc.): ");
        String tipo = scanner.nextLine();

        System.out.print("Ingrese la descripción del peligro: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese la gravedad del peligro (1-5): ");
        int gravedad = scanner.nextInt();
        scanner.nextLine();  // Consumir la nueva línea

        System.out.print("Ingrese la latitud del peligro: ");
        double latitud = scanner.nextDouble();

        System.out.print("Ingrese la longitud del peligro: ");
        double longitud = scanner.nextDouble();

        Peligro nuevoPeligro = new Peligro(tipo, descripcion, latitud, longitud);
        mapa.agregarPeligro(nuevoPeligro);

        System.out.println("Peligro reportado exitosamente.");
    }

    private static void detectarPeligroCercano(Mapa mapa, Scanner scanner) {
        System.out.print("Ingrese su latitud actual: ");
        double latitud = scanner.nextDouble();

        System.out.print("Ingrese su longitud actual: ");
        double longitud = scanner.nextDouble();

        System.out.print("Ingrese el rango de detección en grados (por ejemplo, 0.01): ");
        double rango = scanner.nextDouble();

        ArrayList<Peligro> peligrosCercanos = mapa.getPeligrosCercanos(latitud, longitud, rango);

        if (peligrosCercanos.isEmpty()) {
            System.out.println("No se encontraron peligros cercanos.");
        } else {
            System.out.println("Peligros cercanos detectados:");
            for (Peligro p : peligrosCercanos) {
                System.out.println("Tipo: " + p.getTipo() + ", Descripción: " + p.getDescripcion() +
                                   ", Gravedad: " + p.getGravedad() +
                                   ", Ubicación: (" + p.getLatitud() + ", " + p.getLongitud() + ")");
            }
        }
    }
}

