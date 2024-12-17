/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import spdvi.componentimatge.ImagePanel;
import spdvi.componentimatge.ImagePanelAzure;

/**
 *
 * @author Rulox
 */
public class RetrocedirImatge {
    public static void mostrarImagenAnterior(ImagePanelAzure mainFrame) {
        ArrayList<BufferedImage> bufferedImages = mainFrame.getBufferedImages();
        int currentIndex = mainFrame.getCurrentIndex();
        
        if (!bufferedImages.isEmpty()) {
            currentIndex = (currentIndex - 1 + bufferedImages.size()) % bufferedImages.size(); // Retroceder al índice anterior
            mainFrame.setCurrentIndex(currentIndex); // Actualizar el índice y la imagen actual
            mainFrame.setCurrentImage(bufferedImages.get(currentIndex)); // Mostrar la nueva imagen
        }
    }
    
}
