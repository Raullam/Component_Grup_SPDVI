/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import spdvi.componentimatge.ImagePanel;
import javax.swing.*;

public class MostrarDimensions {

    // Método estático que se llama desde el botón
    public static void mostrarDimensions(ImagePanel imagePanel) {
        // Obtener las dimensiones de la imagen desde ImagePanel
        String dimensions = imagePanel.getCurrentImageDimensions();
        // Mostrar las dimensiones en un cuadro de mensaje
        JOptionPane.showMessageDialog(null, dimensions, "Dimensiones de la Imagen", JOptionPane.INFORMATION_MESSAGE);
    }
}
