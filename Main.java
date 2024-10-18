public class Main {
    public static void main(String[] args) {
        // Llamar a la interfaz gráfica del sistema (MainGUI)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}
