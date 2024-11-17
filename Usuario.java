import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Usuario {
    private String nombre;
    private int puntos;
    private String zona;

    // Constructor
    public Usuario(String nombre, int puntos, String zona) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.zona = zona;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return nombre + "," + puntos + "," + zona; // Formato para el archivo CSV
    }

    // Métodos de gestión de usuarios
    public static List<Usuario> cargarUsuariosDesdeCSV(String archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(archivo);

        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("Archivo no encontrado: " + archivo);
            return usuarios; // devuelve una lista vacía si no existe el archivo
        }


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) { // Validamos que tenga nombre, puntos y zona
                    String nombre = datos[0];
                    int puntos = Integer.parseInt(datos[1]);
                    String zona = datos[2];
                    usuarios.add(new Usuario(nombre, puntos, zona));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static void guardarUsuariosEnCSV(String archivo, List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(archivo)) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario iniciarSesion(List<Usuario> usuarios) {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre para iniciar sesión:");
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + nombre + ". Tus puntos actuales son: " + usuario.getPuntos());
                return usuario;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario no encontrado. Por favor, registrese.");
        return null;
    }

    public static Usuario registrarUsuario(String archivo, List<Usuario> usuarios) {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre para registrarse:");
        String zona = JOptionPane.showInputDialog("Ingrese su zona:");
        Usuario nuevoUsuario = new Usuario(nombre, 0, zona);
        usuarios.add(nuevoUsuario);
        guardarUsuariosEnCSV(archivo, usuarios);
        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
        return nuevoUsuario;
    }
}
