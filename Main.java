public class Main {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI(); //solo es necesario llamar a la interfaz gráfica para que el programa corra
            }
        });
    }
},
