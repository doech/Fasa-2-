import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainGUI extends JFrame {
    private Mapa mapa;
    private JTextArea displayArea;
    private JLabel mapLabel;
    private static final String USUARIOS_CSV = "usuarios.csv";
    private Usuario usuarioActual;
    private List<Usuario> usuarios;
    private ImageIcon mapIcon;
    private List<Point> dangerPoints;
    private JButton marcarReparadoConClickButton;
    private Calles calles;

    private static final String FILE_PATH = "peligros.csv";
    private final Color LILA_CLARO = new Color(221, 160, 221);
    private final Color LILA_OSCURO = new Color(153, 50, 204);

    public MainGUI() {
        mapa = new Mapa();
        verificarArchivoUsuarios();
        usuarios = Usuario.cargarUsuariosDesdeCSV(USUARIOS_CSV); // Cargar los usuarios desde el archivo CSV
        dangerPoints = new ArrayList<>();
        calles = new Calles();

         // Verificar si el usuario puede iniciar sesión o necesita registrarse
         usuarioActual = Usuario.iniciarSesion(usuarios);
         if (usuarioActual == null) { // Si no hay usuario registrado, solicitar registro
             usuarioActual = mostrarDialogoCrearUsuario();
             if (usuarioActual != null) {
                 usuarios.add(usuarioActual);
                 Usuario.guardarUsuariosEnCSV(USUARIOS_CSV, usuarios); // Guardar el nuevo usuario en el CSV
             } else {
                 JOptionPane.showMessageDialog(null, "Es obligatorio registrar un usuario para continuar.");
                 System.exit(0); // Cerrar el programa si no se registra un usuario
             }
         }

        setTitle("Sistema de Detección de Baches y Peligros");
        setTitle("RADAR");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


//Cargar las calles desde los archivos CSV
        calles.cargarCallesDesdeCSV("Calles_zona14.csv");
        calles.cargarCallesDesdeCSV("Calles_zona15.csv");
        calles.cargarCallesDesdeCSV("Calles_zona16.csv");        
    
        // Inicializar el mapa con una imagen predeterminada

        // Inicializar el mapa
        mapIcon = new ImageIcon("mapa.png");
        mapLabel = new JLabel(mapIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                for (Point point : dangerPoints) {
                    g.fillOval(point.x, point.y, 10, 10); // Dibujar peligros como puntos rojos
                }
            }
        };

        // Cargar peligros desde el archivo CSV
        cargarPeligrosDesdeCSV();

        // Mostrar los peligros de la zona del usuario
        cargarMapaPorZona(usuarioActual.getZona());
    
        // Panel principal para el mapa
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(mapLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Configurar eventos para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarPeligrosEnCSV();
            }
        });
    
        // Configurar clics en el mapa
        mapLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
        
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Marcar como reparado con clic derecho
                    Graphics g = mapLabel.getGraphics();
                    g.setColor(Color.GREEN);
                    g.fillOval(clickPoint.x - 5, clickPoint.y - 5, 20, 20);  // Dibuja un círculo verde más grande
                    JOptionPane.showMessageDialog(null, "Peligro marcado como reparado");
                } else {
                    //Validar calles al reportar un peligro
                    String avenida = JOptionPane.showInputDialog("Ingrese la Avenida del peligro:");
                    String calle = JOptionPane.showInputDialog("Ingrese la Calle del peligro:");

                    if (avenida != null && calle != null) {
                        if (calles.validarCalle(calle)) { // MODIFICACIÓN: Validación con Calles
                            String descripcion = JOptionPane.showInputDialog("Ingrese la Descripción del peligro:");
                            if (descripcion != null) {
                                String zona = usuarioActual.getZona(); // Zona del usuario actual
                                Peligro nuevoPeligro = new Peligro(avenida, calle, descripcion, zona);
                                mapa.registrarPeligro(nuevoPeligro);
                                dangerPoints.add(clickPoint); // Añadir el punto al mapa
                                mapLabel.repaint(); // Repintar el mapa
                            }
                        } else {
                            //Mensaje de error si la calle no existe
                            JOptionPane.showMessageDialog(null, "La calle ingresada no existe.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar una avenida y calle válidas.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        // Añadir botones
        JPanel buttonPanel = crearPanelBotones();
        add(buttonPanel, BorderLayout.SOUTH);
    
        // Área de texto para mostrar peligros
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setBackground(new Color(221, 160, 221));
        displayArea.setForeground(new Color(153, 50, 204));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.EAST);
    }
    

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(LILA_CLARO);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton reportarButton = crearBoton("Reportar Peligro");
        JButton mostrarButton = crearBoton("Mostrar Peligros");
        marcarReparadoConClickButton = crearBoton("Marcar Reparado con Click");
        JButton calificarButton = crearBoton("Calificar Reparación");
        JButton puntosButton = crearBoton("Ver Puntos del Usuario");
        JButton guardarCSVButton = crearBoton("Guardar en CSV");

        buttonPanel.add(reportarButton);
        buttonPanel.add(mostrarButton);
        buttonPanel.add(marcarReparadoConClickButton);
        buttonPanel.add(calificarButton);
        buttonPanel.add(puntosButton);
        buttonPanel.add(guardarCSVButton);

        reportarButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Haga clic en el mapa para reportar el peligro."));
        mostrarButton.addActionListener(e -> mostrarPeligros());
        marcarReparadoConClickButton.addActionListener(e -> marcarPeligroReparadoConClick());
        calificarButton.addActionListener(e -> calificarReparacion());
        puntosButton.addActionListener(e -> mostrarPuntosUsuario());
        guardarCSVButton.addActionListener(e -> guardarPeligrosEnCSV());
        return buttonPanel;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(LILA_OSCURO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        return boton;
    }

    private void verificarArchivoUsuarios() {
        File file = new File(USUARIOS_CSV);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("nombre,zona,puntos\n"); // Encabezados del archivo
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
    }

    
    private Usuario mostrarDialogoCrearUsuario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Logo
        ImageIcon logoIcon = new ImageIcon("radar.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);
    
        // Campos de entrada
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
    
        JTextField nombreField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nombreField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Zona:"), gbc);
    
        JComboBox<String> zonaBox = new JComboBox<>(new String[]{"14", "15", "16"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(zonaBox, gbc);
    
        // Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String zona = (String) zonaBox.getSelectedItem();
    
            if (!nombre.isEmpty() && zona != null) {
                return new Usuario(nombre, 0, zona);
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            }
        }
        return null; // Si el usuario cancela el registro
    }
    
    
    
    

    private void cargarMapaPorZona(String zona) {
        String nombreMapa;

        if ("14".equals(zona)) {
            nombreMapa = "mapa14.png";
        } else if ("15".equals(zona)) {
            nombreMapa = "mapa15.png";
        } else if ("16".equals(zona)) {
            nombreMapa = "mapa16.png";
        } else {
            nombreMapa = "mapa.png";
            JOptionPane.showMessageDialog(this, "Zona no reconocida. Cargando mapa predeterminado.");
        }

        mapIcon = new ImageIcon(nombreMapa);
        mapLabel.setIcon(mapIcon);
        
    }

    
    // Método para mostrar los puntos del usuario actual
    private void mostrarPuntosUsuario() {
        if (usuarioActual != null) {
            JOptionPane.showMessageDialog(this, 
                "Tus puntos actuales son: " + usuarioActual.getPuntos(), 
                "Puntos del Usuario", 
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontró un usuario activo.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    

    
    // Método para reportar un peligro
    private void reportarPeligro() {
        JOptionPane.showMessageDialog(null, "Haga clic en el mapa para reportar el peligro.");
    }

    // Método para mostrar los peligros registrados en el área de texto y en el mapa
    private void mostrarPeligros() {
    List<Peligro> peligros = mapa.getPeligros();

    if (peligros.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay peligros registrados.", "Peligros", JOptionPane.INFORMATION_MESSAGE);
    } else {
        StringBuilder mensaje = new StringBuilder();
        for (Peligro peligro : peligros) {
            mensaje.append("Avenida: ").append(peligro.getAvenida())
                   .append(", Calle: ").append(peligro.getCalle())
                   .append(", Descripción: ").append(peligro.getDescripcion())
                   .append(", Reparado: ").append(peligro.isReparado() ? "Sí" : "No")
                   .append(", Calificación: ").append(peligro.getCalificacion())
                   .append(", Fecha de Registro: ").append(peligro.getFechaRegistro()) // Muestra la fecha de registro
                   .append(", Tiempo Aproximado de Reparación: ").append(peligro.getTiempoAproximadoReparacion())
                   .append(" día(s)") // Muestra el tiempo aproximado de reparación en días
                   .append("\n\n");
        }
        
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Lista de Peligros", JOptionPane.INFORMATION_MESSAGE);
    }
}
 

    // Método para guardar los peligros en un archivo CSV
    private void guardarPeligrosEnCSV() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // Crear las carpetas si no existen
    
        try (FileWriter writer = new FileWriter(file)) {
            for (Peligro peligro : mapa.getPeligros()) {
                String avenida = peligro.getAvenida();
                String calle = peligro.getCalle();
                String descripcion = peligro.getDescripcion();
                boolean reparado = peligro.isReparado();
                int calificacion = peligro.getCalificacion();
                String zona = peligro.getZona();
                Point coordenadas = obtenerCoordenadasDesdeCalle(avenida, calle); // coordenadas
                int x = coordenadas != null ? coordenadas.x : -1; // Coordenada X
                int y = coordenadas != null ? coordenadas.y : -1; // Coordenada Y
                
                writer.write(String.join(",", avenida, calle, descripcion, String.valueOf(reparado), 
                                           String.valueOf(calificacion), zona, String.valueOf(x), 
                                           String.valueOf(y)) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Peligros guardados en " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar en CSV: " + e.getMessage());
        }
    }
    

    private Point obtenerCoordenadasDesdeCalle(String avenida, String calle) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) { // Confirma que en el archivo hay 8 campos o columnas
                    String avenidaCsv = data[0];
                    String calleCsv = data[1];
                    int x = Integer.parseInt(data[6]); // Columna de coordenada x
                    int y = Integer.parseInt(data[7]); // Columna de coordenada y
    
                    // Comparar con los datos proporcionados
                    if (avenidaCsv.equals(avenida) && calleCsv.equals(calle)) {
                        return new Point(x, y);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de peligros.");
        }
        return null; // Si no se encuentra la combinación
    }
    
    // Método para cargar los peligros desde un archivo CSV al iniciar
    private void cargarPeligrosDesdeCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            line = br.readLine(); // Leer y descartar la primera línea
    
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    boolean reparado = Boolean.parseBoolean(data[3]);
                    if (!reparado) { // Solo los no reparados
                        int x = Integer.parseInt(data[6].trim());
                        int y = Integer.parseInt(data[7].trim());
                        dangerPoints.add(new Point(x, y));
                    }
                }
            }
            mapLabel.repaint(); // Repintar el mapa
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar peligros desde el archivo.");
        }
    }
    
    
    //Método para repintar el mapa para marcar un peligro reparado
    private void marcarPeligroReparadoConClick() {
        JTextField avenidaField = new JTextField(15);
        JTextField calleField = new JTextField(15);
    
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Avenida:"), gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(avenidaField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Calle:"), gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(calleField, gbc);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Marcar Peligro como Reparado", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String avenida = avenidaField.getText();
            String calle = calleField.getText();
    
            // Buscar el peligro que coincida con la avenida y calle ingresadas
            boolean peligroEncontrado = false;
            Point puntoReparado = null;
    
            for (int i = 0; i < mapa.getPeligros().size(); i++) {
                Peligro peligro = mapa.getPeligros().get(i);
                if (peligro.getAvenida().equals(avenida) && peligro.getCalle().equals(calle) && !peligro.isReparado()) {
                    peligro.setReparado(true);  // Marcar el peligro como reparado
                    peligroEncontrado = true;
                    puntoReparado = dangerPoints.get(i);  // Obtener el punto correspondiente al peligro en el mapa
                    break;
                }
            }
    
            if (peligroEncontrado && puntoReparado != null) {
                // Dibujar un círculo verde sobre el peligro para indicar que ha sido reparado
                Graphics g = mapLabel.getGraphics();
                g.setColor(Color.GREEN);
                g.fillOval(puntoReparado.x - 5, puntoReparado.y - 5, 20, 20);  // Dibuja un círculo más grande en verde
    
                mapLabel.repaint();  // Repintar el mapa para reflejar el cambio
                JOptionPane.showMessageDialog(null, "Peligro encontrado, haz click derecho en el mapa para marcarlo como reparado.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un peligro en la avenida y calle especificadas.");
            }
        }
    }



    // Método para calificar la reparación de un peligro
    private void calificarReparacion() {
        JTextField avenidaField = new JTextField(15);
        JTextField calleField = new JTextField(15);
        JTextField calificacionField = new JTextField(5);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Avenida:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(avenidaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Calle:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(calleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Calificación (1-10):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(calificacionField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "Calificar Reparación", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String avenida = avenidaField.getText();
            String calle = calleField.getText();
            int calificacion;

            try {
                calificacion = Integer.parseInt(calificacionField.getText());
                if (calificacion < 1 || calificacion > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La calificación debe ser un número entre 1 y 10.");
                return;
            }

            mapa.calificarPeligro(avenida, calle, calificacion);
            if (usuarioActual != null) {
                usuarioActual.agregarPuntos(10);
                JOptionPane.showMessageDialog(null, "Reparación calificada. Has ganado 10 puntos. Tus puntos actuales: " + usuarioActual.getPuntos());
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
