/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

/**
 *
 * @author 34666
 */

import spdvi.componentimatge.ImagePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarDimensions implements ActionListener {
    private final ImagePanel imagePanel;

    public MostrarDimensions(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener las dimensiones de la imagen
        String dimensions = imagePanel.getCurrentImageDimensions();
        // Mostrar las dimensiones en un cuadro de mensaje
        JOptionPane.showMessageDialog(null, dimensions, "Dimensiones de la Imagen", JOptionPane.INFORMATION_MESSAGE);
    }
}
