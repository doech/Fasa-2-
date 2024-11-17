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
    private Usuario usuarioActual;
    private List<Usuario> usuarios;
    private ImageIcon mapIcon;
    private List<Point> dangerPoints;
    private JButton marcarReparadoConClickButton;

    private static final String FILE_PATH = "peligros.csv";
    private final Color LILA_CLARO = new Color(221, 160, 221);
    private final Color LILA_OSCURO = new Color(153, 50, 204);

    public MainGUI() {
        mapa = new Mapa();
        usuarios = new ArrayList<>();
        dangerPoints = new ArrayList<>();
        setTitle("Sistema de Detección de Baches y Peligros");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        // Inicializar el mapa con una imagen predeterminada
        mapIcon = new ImageIcon("mapa.png");
        mapLabel = new JLabel(mapIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                for (Point point : dangerPoints) {
                    g.fillOval(point.x, point.y, 10, 10);
                }
            }
        };
    
        // Panel principal para el mapa
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(mapLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    
        // Cargar peligros desde el archivo CSV al iniciar
        cargarPeligrosDesdeCSV();
    
        // Crear un usuario al inicio
        mostrarDialogoCrearUsuario();
    
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
                    // Reportar nuevo peligro con clic izquierdo
                    String avenida = JOptionPane.showInputDialog("Ingrese la Avenida del peligro:");
                    String calle = JOptionPane.showInputDialog("Ingrese la Calle del peligro:");
                    String descripcion = JOptionPane.showInputDialog("Ingrese la Descripción del peligro:");
        
                    if (avenida != null && calle != null && descripcion != null) {
                        // Asegurarse de obtener la zona del usuario actual
                        String zona = usuarioActual.getZona(); // Obtén la zona del usuario registrado
        
                        // Crear el nuevo peligro con la zona del usuario
                        Peligro nuevoPeligro = new Peligro(avenida, calle, descripcion, zona);
                        mapa.registrarPeligro(nuevoPeligro);
                        dangerPoints.add(clickPoint);  // Añadir la posición donde se hizo clic a la lista de puntos de peligro
                        mapLabel.repaint();  // Repintar el mapa para mostrar los marcadores
                    } else {
                        // Mensaje de error personalizado
                        JOptionPane.showMessageDialog(null, "El número de calle o avenida que puso no existe.", "Error de validación", JOptionPane.ERROR_MESSAGE);
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

    
    

    private void mostrarDialogoCrearUsuario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel logoLabel = new JLabel(new ImageIcon("radar.png"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

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

        JTextField zonaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(zonaField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String zona = zonaField.getText();

            if (!nombre.isEmpty() && !zona.isEmpty()) {
                usuarioActual = new Usuario(nombre, "Avenida N/A", "Calle N/A", zona);
                usuarios.add(usuarioActual);
                cargarMapaPorZona(zona);
                JOptionPane.showMessageDialog(this, "Usuario creado: " + nombre);
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            }
        }
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
            JOptionPane.showMessageDialog(null, "Tus puntos actuales son: " + usuarioActual.getPuntos());
        } else {
            JOptionPane.showMessageDialog(null, "No hay usuario registrado.");
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
        file.getParentFile().mkdirs();  // Crear las carpetas si no existen

        try (FileWriter writer = new FileWriter(file)) {
            List<Peligro> peligros = mapa.getPeligros();
            for (Peligro peligro : peligros) {
                writer.write(peligro.getAvenida() + "," + peligro.getCalle() + "," + peligro.getDescripcion() + "," + peligro.isReparado() + "," + peligro.getCalificacion() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Peligros guardados en " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();  // Imprime el error en la consola para depuración
            JOptionPane.showMessageDialog(null, "Error al guardar en CSV: " + e.getMessage());
        }
    }

    // Método para cargar los peligros desde un archivo CSV al iniciar
    private void cargarPeligrosDesdeCSV() {
    File file = new File(FILE_PATH);
    if (!file.exists()) return;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 6) { // Asegurarse de que haya seis elementos en la línea
                String avenida = data[0];
                String calle = data[1];
                String descripcion = data[2];
                boolean reparado = Boolean.parseBoolean(data[3]);
                int calificacion = Integer.parseInt(data[4]);
                String zona = data[5]; // Leer la zona del archivo CSV

                // Crear el objeto Peligro usando el valor de zona del archivo
                Peligro peligro = new Peligro(avenida, calle, descripcion, zona);
                peligro.setReparado(reparado);
                peligro.setCalificacion(calificacion);
                mapa.registrarPeligro(peligro);
            }
        }
        System.out.println("Peligros cargados desde " + FILE_PATH);
    } catch (IOException e) {
        System.err.println("Error al cargar desde CSV: " + e.getMessage());
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
