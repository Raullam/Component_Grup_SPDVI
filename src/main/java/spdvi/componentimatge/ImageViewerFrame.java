/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.componentimatge;

/**
 *
 * @author Rulox
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImageViewerFrame extends JFrame {
    private ImagePanel imagePanel;
    private JButton btnLoad, btnResize, btnClear, btnRotate, btnSave;

    public ImageViewerFrame() {
        setTitle("Image Viewer Mejorado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel de la imagen
        imagePanel = new ImagePanel();

        // Crear los botones
        btnLoad = new JButton("Cargar Imagen");
        btnResize = new JButton("Redimensionar");
        btnClear = new JButton("Limpiar Imagen");
        btnRotate = new JButton("Rotar Imagen");
        btnSave = new JButton("Guardar Imagen");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnResize);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRotate);
        buttonPanel.add(btnSave);

        // Añadir componentes al frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Añadir funcionalidad a los botones
        btnLoad.addActionListener(e -> cargarImagen());
        btnResize.addActionListener(e -> redimensionarImagen());
        btnClear.addActionListener(e -> limpiarImagen());
        btnRotate.addActionListener(e -> rotarImagen());
        btnSave.addActionListener(e -> guardarImagen());

        // Añadir funcionalidad de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_L: // L para cargar imagen
                        cargarImagen();
                        break;
                    case KeyEvent.VK_R: // R para redimensionar
                        redimensionarImagen();
                        break;
                    case KeyEvent.VK_C: // C para limpiar imagen
                        limpiarImagen();
                        break;
                    case KeyEvent.VK_T: // T para rotar imagen
                        rotarImagen();
                        break;
                    case KeyEvent.VK_S: // S para guardar imagen
                        guardarImagen();
                        break;
                }
            }
        });

        // Hacer que el frame escuche teclas
        setFocusable(true);

        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }

    private void cargarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            imagePanel.loadImage(imagePath);
        }
    }

    private void redimensionarImagen() {
        String inputWidth = JOptionPane.showInputDialog(this, "Introduce el ancho:");
        String inputHeight = JOptionPane.showInputDialog(this, "Introduce la altura:");
        try {
            int width = Integer.parseInt(inputWidth);
            int height = Integer.parseInt(inputHeight);
            imagePanel.resizeImage(width, height);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarImagen() {
        imagePanel.clearImage();
    }

    private void rotarImagen() {
        imagePanel.rotateImage(90); // Rotar 90 grados
    }

    private void guardarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String outputPath = fileChooser.getSelectedFile().getAbsolutePath();
            imagePanel.saveImage(outputPath);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageViewerFrame frame = new ImageViewerFrame();
            frame.setVisible(true);
        });
    }
}

