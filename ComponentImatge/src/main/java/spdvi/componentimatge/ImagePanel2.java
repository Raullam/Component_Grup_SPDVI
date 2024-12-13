/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.componentimatge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePanel2 extends JPanel {
    private BufferedImage originalImage; // Imagen original
    private BufferedImage currentImage;  // Imagen redimensionada actual
    private String imagePath; // Ruta de la imagen
    private double rotationAngle; // Ángulo de rotación

    public ImagePanel2() {
        this.originalImage = null;
        this.currentImage = null;
        this.imagePath = null;
        this.rotationAngle = 0;
        this.setPreferredSize(new Dimension(400, 300));
    }

    // Cargar imagen
    public void loadImage(String imagePath) {
        try {
            this.originalImage = ImageIO.read(new File(imagePath));
            this.currentImage = this.originalImage;  // Al principio, la imagen actual es la original
            this.imagePath = imagePath;
            this.rotationAngle = 0;

            // Ajustar el tamaño del panel según la imagen original
            if (this.originalImage != null) {
                this.setPreferredSize(new Dimension(originalImage.getWidth(), originalImage.getHeight()));
                this.revalidate(); // Redibujar el panel para que se ajuste al tamaño de la imagen
            }
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mostrar dimensiones actuales de la imagen
    public String getCurrentImageDimensions() {
        if (currentImage != null) {
            return "Dimensiones: " + currentImage.getWidth() + "x" + currentImage.getHeight();
        } else {
            return "No hay imagen cargada.";
        }
    }

    // Redimensionar la imagen usando la imagen original
    public void resizeImage(int width, int height) {
        if (originalImage != null) {
            // Redimensionar con la imagen original
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Convertir la imagen escalada a un BufferedImage para mejor control
            BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            // Asignar la nueva imagen redimensionada
            this.currentImage = buffered;

            // Ajustar el tamaño del panel según el nuevo tamaño de la imagen
            this.setPreferredSize(new Dimension(width, height));
            this.revalidate();  // Redibujar el panel
            this.repaint();
        }
    }

    // Rotar la imagen
    public void rotateImage(double angle) {
        if (currentImage != null) {
            this.rotationAngle += angle;
            repaint();
        }
    }

    // Guardar la imagen
    public void saveImage(String outputPath) {
        if (currentImage != null) {
            try {
                ImageIO.write(currentImage, "png", new File(outputPath));
                JOptionPane.showMessageDialog(this, "Imagen guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Limpiar la imagen
    public void clearImage() {
        this.originalImage = null;
        this.currentImage = null;
        this.rotationAngle = 0;
        repaint();
    }

    // Dibujar la imagen en el panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Aplicar la rotación
            AffineTransform transform = new AffineTransform();
            transform.translate(centerX, centerY);
            transform.rotate(Math.toRadians(rotationAngle));
            transform.translate(-currentImage.getWidth() / 2.0, -currentImage.getHeight() / 2.0);

            g2d.drawImage(currentImage, transform, null);
            g2d.dispose();
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.red);
            g.drawString("Imagen borrada", 10, 20);
        }
    }
}
