package paquete_principal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
                gui.compareAsymptoticNotation(); // Llama a la comparación de notación asintótica al iniciar 
            }
        });
    }
}