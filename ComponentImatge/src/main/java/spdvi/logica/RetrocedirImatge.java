/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import spdvi.componentimatge.ImagePanel;

/**
 *
 * @author Rulox
 */
public class RetrocedirImatge {
    public static void mostrarImagenAnterior(ArrayList<BufferedImage> bufferedImages, int currentIndex, ImagePanel imagePanel) {
    if (!bufferedImages.isEmpty()) {
        // Decrementar el índice y asegurarse de que no se desborde
        currentIndex = (currentIndex - 1 + bufferedImages.size()) % bufferedImages.size();
        imagePanel.loadImage2(bufferedImages.get(currentIndex)); // Cargar la imagen anterior
    }
}
    
}
