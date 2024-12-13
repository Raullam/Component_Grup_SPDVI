/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.componentimatge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ImageViewerArray2 extends JFrame {
    private ImagePanel2 imagePanel;
    private JButton btnLoad, btnResize, btnClear, btnRotate, btnSave, btnNext, btnPrevious;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0; // Índice de la imagen actual

    public ImageViewerArray2() {
        setTitle("Image Viewer Mejorado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel de la imagen
        imagePanel = new ImagePanel2();

        // Crear los botones
        btnLoad = new JButton("Cargar Imagen");
        btnResize = new JButton("Redimensionar");
        btnClear = new JButton("Limpiar Imagen");
        btnRotate = new JButton("Rotar Imagen");
        btnSave = new JButton("Guardar Imagen");
        btnNext = new JButton("Adelante");
        btnPrevious = new JButton("Atrás");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnResize);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRotate);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnPrevious);

        // Añadir componentes al frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Añadir funcionalidad a los botones
        btnLoad.addActionListener(e -> cargarImagen());
        btnResize.addActionListener(e -> redimensionarImagen());
        btnClear.addActionListener(e -> limpiarImagen());
        btnRotate.addActionListener(e -> rotarImagen());
        btnSave.addActionListener(e -> guardarImagen());
        btnNext.addActionListener(e -> mostrarSiguienteImagen());
        btnPrevious.addActionListener(e -> mostrarImagenAnterior());

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
                    case KeyEvent.VK_RIGHT: // Flecha derecha para la siguiente imagen
                        mostrarSiguienteImagen();
                        break;
                    case KeyEvent.VK_LEFT: // Flecha izquierda para la imagen anterior
                        mostrarImagenAnterior();
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
        fileChooser.setMultiSelectionEnabled(true); // Permite seleccionar varias imágenes
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Agregar todas las imágenes seleccionadas al array
            for (var file : fileChooser.getSelectedFiles()) {
                imagePaths.add(file.getAbsolutePath());
            }
            // Mostrar la primera imagen si existe alguna
            if (!imagePaths.isEmpty()) {
                currentIndex = 0; // Restablecer al primer índice
                imagePanel.loadImage(imagePaths.get(currentIndex)); // Cargar la primera imagen
            }
        }
    }

    private void mostrarSiguienteImagen() {
        if (!imagePaths.isEmpty()) {
            currentIndex = (currentIndex + 1) % imagePaths.size(); // Asegura el ciclo de imágenes
            imagePanel.loadImage(imagePaths.get(currentIndex));
        }
    }

    private void mostrarImagenAnterior() {
        if (!imagePaths.isEmpty()) {
            currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size(); // Asegura el ciclo de imágenes
            imagePanel.loadImage(imagePaths.get(currentIndex));
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
    
    private void limpiarImagen() {
        imagePanel.clearImage();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageViewerArray2 frame = new ImageViewerArray2();
            frame.setVisible(true);
        });
    }
}
