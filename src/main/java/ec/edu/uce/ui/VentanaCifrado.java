package ec.edu.uce.ui;

import ec.edu.uce.cifrados.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaCifrado extends JFrame {

    private JComboBox<String> comboCifrados;
    private JLabel lblDescripcion;
    private JTextField txtClave;
    private JTextArea txtEntrada, txtSalida;
    private JButton btnProcesar;
    private JRadioButton rbCifrar, rbDescifrar; // <-- NUEVOS SELECTORES

    private String hintClave = "";
    private final String HINT_ENTRADA = "Escribe el texto aquí...";

    public VentanaCifrado() {
        setTitle("Sistema de Cifrado Clásico");
        setSize(700, 550); // Un poco más alta para que quepan los botones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // PANEL SUPERIOR
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel lblTitulo = new JLabel("CIFRADO CLÁSICO");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNorte.add(lblTitulo);
        panelNorte.add(Box.createVerticalStrut(15));

        String[] metodos = {"César", "Atbash", "Vigenère", "Playfair", "Rail Fence"};
        comboCifrados = new JComboBox<>(metodos);
        comboCifrados.setMaximumSize(new Dimension(300, 30));
        panelNorte.add(comboCifrados);
        panelNorte.add(Box.createVerticalStrut(10));

        lblDescripcion = new JLabel("Selecciona un método.");
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setFont(new Font("SansSerif", Font.ITALIC, 12));
        panelNorte.add(lblDescripcion);
        panelNorte.add(Box.createVerticalStrut(15));

        // Panel de Clave
        JPanel panelClave = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtClave = new JTextField(20);
        panelClave.add(txtClave);
        panelNorte.add(panelClave);

        // NUEVO: Panel de Modo (Cifrar o Descifrar)
        JPanel panelModo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rbCifrar = new JRadioButton("Cifrar", true); // Seleccionado por defecto
        rbDescifrar = new JRadioButton("Descifrar");
        ButtonGroup grupoModo = new ButtonGroup();
        grupoModo.add(rbCifrar);
        grupoModo.add(rbDescifrar);
        panelModo.add(rbCifrar);
        panelModo.add(rbDescifrar);
        panelNorte.add(panelModo);

        add(panelNorte, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        txtEntrada = new JTextArea();
        txtEntrada.setLineWrap(true);
        configurarPlaceholder(txtEntrada, HINT_ENTRADA);
        JScrollPane scrollEntrada = new JScrollPane(txtEntrada);
        scrollEntrada.setBorder(BorderFactory.createTitledBorder("Texto de Entrada")); // Nombre Neutro
        panelCentro.add(scrollEntrada);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        btnProcesar = new JButton("Procesar ->"); // Nombre Neutro
        btnProcesar.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelBotones.add(btnProcesar);
        panelCentro.add(panelBotones);

        txtSalida = new JTextArea();
        txtSalida.setLineWrap(true);
        txtSalida.setEditable(false);
        JScrollPane scrollSalida = new JScrollPane(txtSalida);
        scrollSalida.setBorder(BorderFactory.createTitledBorder("Texto de Salida")); // Nombre Neutro
        panelCentro.add(scrollSalida);

        add(panelCentro, BorderLayout.CENTER);

        // EVENTOS
        txtClave.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtClave.getText().equals(hintClave)) {
                    txtClave.setText("");
                    txtClave.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtClave.getText().isEmpty()) {
                    txtClave.setForeground(Color.GRAY);
                    txtClave.setText(hintClave);
                }
            }
        });

        comboCifrados.addActionListener(e -> actualizarMenu());
        actualizarMenu();

        btnProcesar.addActionListener(e -> ejecutarCifrado());
    }

    private void configurarPlaceholder(JTextArea area, String hint) {
        area.setText(hint);
        area.setForeground(Color.GRAY);
        area.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (area.getText().equals(hint)) {
                    area.setText("");
                    area.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (area.getText().isEmpty()) {
                    area.setForeground(Color.GRAY);
                    area.setText(hint);
                }
            }
        });
    }

    private void actualizarMenu() {
        String seleccion = (String) comboCifrados.getSelectedItem();
        boolean requiereClave = true;
        txtSalida.setText("");

        switch (seleccion) {
            case "César":
                lblDescripcion.setText("Desplaza las letras un número fijo de posiciones.");
                hintClave = "Ingresa un número (Ej: 5)";
                break;
            case "Atbash":
                lblDescripcion.setText("Invierte el abecedario (A->Z, B->Y). NO requiere clave.");
                hintClave = "No requiere clave";
                requiereClave = false;
                break;
            case "Vigenère":
                lblDescripcion.setText("Usa una palabra clave para un desplazamiento variable.");
                hintClave = "Ingresa una palabra (Ej: SECRETO)";
                break;
            case "Playfair":
                lblDescripcion.setText("Encripta pares de letras usando una matriz 5x5.");
                hintClave = "Ingresa una palabra clave";
                break;
            case "Rail Fence":
                lblDescripcion.setText("Escribe el texto en zigzag usando varios rieles.");
                hintClave = "Ingresa número de rieles (Ej: 3)";
                break;
        }

        txtClave.setEnabled(requiereClave);
        txtClave.setForeground(Color.GRAY);
        txtClave.setText(hintClave);
    }

    private void ejecutarCifrado() {
        String texto = txtEntrada.getText();
        String claveStr = txtClave.getText();
        String seleccion = (String) comboCifrados.getSelectedItem();
        Cifrador miCifrado = null;

        if (texto.trim().isEmpty() || texto.equals(HINT_ENTRADA)) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un texto para procesar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (claveStr.equals(hintClave)) claveStr = "";

        try {
            switch (seleccion) {
                case "César":
                    miCifrado = new CifradoCesar(Integer.parseInt(claveStr));
                    break;
                case "Atbash":
                    miCifrado = new CifradoAtbash();
                    break;
                case "Vigenère":
                    if (claveStr.isEmpty()) throw new IllegalArgumentException("La clave no puede estar vacía.");
                    miCifrado = new CifradoVigenere(claveStr);
                    break;
                case "Playfair":
                    if (claveStr.isEmpty()) throw new IllegalArgumentException("La clave no puede estar vacía.");
                    miCifrado = new CifradoPlayfair(claveStr);
                    break;
                case "Rail Fence":
                    miCifrado = new CifradoRailFence(Integer.parseInt(claveStr));
                    break;
            }

            if (miCifrado != null) {
                // AQUÍ SE DECIDE SI CIFRAR O DESCIFRAR
                if (rbCifrar.isSelected()) {
                    txtSalida.setText(miCifrado.cifrar(texto));
                } else {
                    txtSalida.setText(miCifrado.descifrar(texto));
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Para " + seleccion + ", la clave debe ser un NÚMERO.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "️ -* " + ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, " Ocurrió un error inesperado al procesar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}