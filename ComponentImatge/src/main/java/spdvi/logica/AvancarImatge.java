/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import spdvi.componentimatge.ImagePanel;

/**
 *
 * @author Rulox
 */
public class AvancarImatge {
    
    public static void mostrarSiguienteImagen(ArrayList<BufferedImage> bufferedImages, int currentIndex, ImagePanel imagePanel) {
    if (!bufferedImages.isEmpty()) {
        // Incrementar el índice y asegurarse de que no se desborde
        currentIndex = (currentIndex + 1) % bufferedImages.size();
        imagePanel.loadImage2(bufferedImages.get(currentIndex)); // Cargar la siguiente imagen
    }
}
    
}
