/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import spdvi.componentimatge.ImagePanel;

/**
 *
 * @author Rulox
 */
public class RedimensionarImatge {
    
    public static void redimensionarImagen(ImagePanel imagePanel, JFrame frame) {
    try {
        String widthInput = JOptionPane.showInputDialog(frame, "Introduce el ancho (en píxeles):");
        if (widthInput == null) return; // User clicked 'Cancel'
        
        String heightInput = JOptionPane.showInputDialog(frame, "Introduce la altura (en píxeles):");
        if (heightInput == null) return; // User clicked 'Cancel'
        
        int width = Integer.parseInt(widthInput.trim());
        int height = Integer.parseInt(heightInput.trim());
        
        if (width <= 0 || height <= 0) {
            JOptionPane.showMessageDialog(frame, "El ancho y la altura deben ser números positivos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        imagePanel.resizeImage(width, height);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
