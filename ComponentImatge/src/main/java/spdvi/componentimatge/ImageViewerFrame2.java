package spdvi.componentimatge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ImageViewerFrame2 extends JFrame {
    private ImagePanel2 imagePanel;
    private JButton btnLoad, btnResize, btnClear, btnRotate, btnSave, btnShowDimensions;

    public ImageViewerFrame2(String imagePath) {
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
        btnShowDimensions = new JButton("Mostrar Dimensiones");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnResize);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRotate);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnShowDimensions); // Añadir botón de mostrar dimensiones

        // Añadir componentes al frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Añadir funcionalidad a los botones
        btnLoad.addActionListener(e -> cargarImagen());
        btnResize.addActionListener(e -> redimensionarImagen());
        btnClear.addActionListener(e -> limpiarImagen());
        btnRotate.addActionListener(e -> rotarImagen());
        btnSave.addActionListener(e -> guardarImagen());
        btnShowDimensions.addActionListener(e -> mostrarDimensiones());

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
                    case KeyEvent.VK_D: // D para mostrar dimensiones
                        mostrarDimensiones();
                        break;
                }
            }
        });

        // Hacer que el frame escuche teclas
        setFocusable(true);

        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }
    
    public ImageViewerFrame2() {
        // Constructor por defecto
        this(null);
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
            imagePanel.resizeImage(width, height); // Redimensionar usando la imagen original
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDimensiones() {
        String dimensions = imagePanel.getCurrentImageDimensions();
        JOptionPane.showMessageDialog(this, dimensions, "Dimensiones de la Imagen", JOptionPane.INFORMATION_MESSAGE);
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
            ImageViewerFrame2 frame = new ImageViewerFrame2();
            frame.setVisible(true);
        });
    }
}
