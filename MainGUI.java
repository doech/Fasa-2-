import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class MainGUI extends JFrame {
    private Mapa mapa;
    private JTextArea displayArea;
    private JLabel mapLabel;
    private Usuario usuarioActual;
    private List<Usuario> usuarios;
    private ImageIcon mapIcon;
    private List<Point> dangerPoints; // Lista de puntos de peligros
    private JButton marcarReparadoConClickButton;

    // Constructor
    public MainGUI() {
        mapa = new Mapa();
        usuarios = new ArrayList<>();
        dangerPoints = new ArrayList<>();  // Lista para almacenar las posiciones de los peligros
        setTitle("Sistema de Detección de Baches y Peligros");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        // Crear un usuario al inicio
        mostrarDialogoCrearUsuario();
    
        // Inicializar el mapa con una imagen predeterminada
        mapIcon = new ImageIcon("mapa.png");
        mapLabel = new JLabel(mapIcon) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);  // Color para los marcadores de peligro
    
                // Dibujar los peligros almacenados en la lista
                for (Point point : dangerPoints) {
                    g.fillOval(point.x, point.y, 10, 10);  // Dibujar un círculo en cada punto de peligro
                }
            }
        };
    
        // Cargar el mapa según la zona del usuario
        if (usuarioActual != null) {
            cargarMapaPorZona(usuarioActual.getZona());
        }
    
        // Agregar un listener para detectar clics en el mapa
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
                        Peligro nuevoPeligro = new Peligro(avenida, calle, descripcion);
                        mapa.registrarPeligro(nuevoPeligro);
                        dangerPoints.add(clickPoint);  // Añadir la posición donde se hizo clic a la lista de puntos de peligro
                        mapLabel.repaint();  // Repintar el mapa para mostrar los marcadores
                    }
                }
            }
        });
    
        // Panel principal para el mapa
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapLabel, BorderLayout.CENTER);
    
        // Panel de botones en la parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
        JButton reportarButton = new JButton("Reportar Peligro");
        JButton mostrarButton = new JButton("Mostrar Peligros");
        JButton guardarCSVButton = new JButton("Guardar en CSV");
        JButton calificarButton = new JButton("Calificar Reparación");
        JButton puntosButton = new JButton("Ver Puntos del Usuario");  // Mostrar los puntos del usuario
        JButton marcarReparadoConClickButton = new JButton("Marcar Reparado con Click");
        
        buttonPanel.add(marcarReparadoConClickButton);
        buttonPanel.add(reportarButton);
        buttonPanel.add(mostrarButton);
        buttonPanel.add(guardarCSVButton);
        buttonPanel.add(calificarButton);
        buttonPanel.add(puntosButton);  // Botón para ver los puntos del usuario
        buttonPanel.add(marcarReparadoConClickButton); //marcar peligro reparado con click
    
        // Añadir el panel de botones al panel principal en la parte inferior
        add(buttonPanel, BorderLayout.SOUTH);
    
        // Área de texto para mostrar peligros
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.EAST);
    
        // Añadir el panel principal con el mapa
        add(mainPanel, BorderLayout.CENTER);
    
        // Agregar listeners para los botones
        reportarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Haga clic en el mapa para reportar el peligro.");  // Mensaje de instrucción
            }
        });
    
        mostrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPeligros();  // Llamamos al método para mostrar los peligros
            }
        });
    
        guardarCSVButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPeligrosEnCSV();  // Guardar los peligros en un archivo CSV
            }
        });
    
        calificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calificarReparacion();  // Calificar reparación del peligro
            }
        });
    
        puntosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPuntosUsuario();  // Mostrar puntos del usuario
            }
        });
        marcarReparadoConClickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                marcarPeligroReparadoConClick();  // Llama al método para marcar peligro reparado con clic
            }
        });
    }
    
    


    // Método para mostrar el logo radar.png en el registro de usuario
    private void mostrarDialogoCrearUsuario() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Agregar el logo en la parte superior
    ImageIcon logoIcon = new ImageIcon("radar.png");
    Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);  // Escalar la imagen
    logoIcon = new ImageIcon(logoImage);
    JLabel logoLabel = new JLabel(logoIcon);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;  // El logo ocupa dos columnas
    panel.add(logoLabel, gbc);

    // Campos de texto para la creación de usuario
    gbc.gridwidth = 1;  // Volver a una columna por componente
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

    int result = JOptionPane.showConfirmDialog(null, panel, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
        String nombre = nombreField.getText();
        String zona = zonaField.getText();

        if (!nombre.isEmpty() && !zona.isEmpty()) {
            // Crear el usuario con la zona especificada
            Usuario nuevoUsuario = new Usuario(nombre, "Avenida N/A", "Calle N/A", zona);
            usuarios.add(nuevoUsuario);
            usuarioActual = nuevoUsuario;
            JOptionPane.showMessageDialog(null, "Usuario creado: " + nombre);
        } else {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
        }
    }
}

    
    //Metodo para cargar mapa por zona
    private void cargarMapaPorZona(String zona) {
        String nombreMapa;
    
        // Seleccionar el archivo de mapa según la zona ingresada
        if (zona.equals("14")) {
            nombreMapa = "mapa14.png";
        } else if (zona.equals("15")) {
            nombreMapa = "mapa15.png";
        } else if (zona.equals("16")) {
            nombreMapa = "mapa16.png";
        } else {
            // Mapa predeterminado si la zona no es una de las esperadas
            nombreMapa = "mapa.png";
            JOptionPane.showMessageDialog(this, "Zona no reconocida. Cargando mapa predeterminado.");
        }
    
        // Cargar la imagen del mapa y establecer el icono en `mapLabel`
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
                   .append(", días aproximados de reparación: ").append(peligro.getTiempoAproximadoReparacion())
                   .append(", Registrado el ").append(peligro.getFechaRegistro()) //
                   .append("\n\n");
        }
        
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Lista de Peligros", JOptionPane.INFORMATION_MESSAGE);
        }
    }   

    // Método para guardar los peligros en un archivo CSV
    private void guardarPeligrosEnCSV() {
        // Cambia esta ruta a una ruta válida en tu sistema
        File file = new File("C:\\ruta_completa\\peligros.csv");
        file.getParentFile().mkdirs();  // Crear las carpetas si no existen

        try (FileWriter writer = new FileWriter(file)) {
            List<Peligro> peligros = mapa.getPeligros();
            for (Peligro peligro : peligros) {
                writer.write(peligro.getAvenida() + "," + peligro.getCalle() + "," + peligro.getDescripcion() + "," + peligro.isReparado() + "," + peligro.getCalificacion() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Peligros guardados en peligros.csv.");
        } catch (IOException e) {
            e.printStackTrace();  // Imprime el error en la consola para depuración
            JOptionPane.showMessageDialog(null, "Error al guardar en CSV: " + e.getMessage());
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

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}
