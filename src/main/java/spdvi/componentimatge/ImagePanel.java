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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image; // Imagen cargada
    private String imagePath; // Ruta de la imagen
    private double rotationAngle; // Ángulo de rotación actual

    public ImagePanel() {
        this.image = null;
        this.imagePath = null;
        this.rotationAngle = 0; // Inicialmente no hay rotación
        this.setPreferredSize(new Dimension(400, 300)); // Tamaño predeterminado
    }

    public void loadImage(String imagePath) {
    try {
        // Cargar la imagen
        this.image = ImageIO.read(new File(imagePath));
        this.imagePath = imagePath;
        this.rotationAngle = 0; // Reiniciar la rotación al cargar una nueva imagen
        
        // Ajustar el tamaño del panel al tamaño de la imagen
        if (this.image != null) {
            this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
            this.revalidate(); // Revalidar el contenedor para que se ajuste al nuevo tamaño
        }
        repaint();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



   public void resizeImage(int width, int height) {
    if (image != null) {
        // Redimensionar la imagen usando getScaledInstance
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Convertir la imagen escalada a un BufferedImage para mejor control
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        
        // Dibujar la imagen redimensionada
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Asignar la nueva imagen redimensionada al panel
        this.image = buffered;

        // Ajustar el tamaño del panel según el nuevo tamaño de la imagen
        this.setPreferredSize(new Dimension(width, height));
        
        // Asegurarse de que el panel se redimensione después de la actualización de la imagen
        this.revalidate(); // Recalcular el layout del panel
        this.repaint(); // Repintar el panel con la imagen redimensionada
    }
}



    public void rotateImage(double angle) {
        if (image != null) {
            this.rotationAngle += angle;
            repaint();
        }
    }

    public void saveImage(String outputPath) {
        if (image != null) {
            try {
                ImageIO.write(image, "png", new File(outputPath));
                JOptionPane.showMessageDialog(this, "Imagen guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void clearImage() {
    this.image = null; // Eliminar la referencia a la imagen cargada
    this.rotationAngle = 0; // Restablecer la rotación
    repaint(); // Actualizar el panel
}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Rotar la imagen según el ángulo actual
            AffineTransform transform = new AffineTransform();
            transform.translate(centerX, centerY);
            transform.rotate(Math.toRadians(rotationAngle));
            transform.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

            g2d.drawImage(image, transform, null);
            g2d.dispose();
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.red);
            g.drawString("Imatge borrada", 10, 20);
        }
    }
}
