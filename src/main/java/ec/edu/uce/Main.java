package ec.edu.uce;

import ec.edu.uce.cifrados.*;
import ec.edu.uce.ui.VentanaCifrado;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities asegura que la interfaz gráfica se ejecute en el hilo correcto (buenas prácticas)
        SwingUtilities.invokeLater(() -> {
            VentanaCifrado ventana = new VentanaCifrado();
            ventana.setVisible(true);
        });
    }
}
