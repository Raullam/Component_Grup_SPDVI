/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi;

import javax.swing.SwingUtilities;
import spdvi.componentimatge.ImagePanelAzure;

/**
 *
 * @author Rulox
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImagePanelAzure frame = new ImagePanelAzure();
            frame.setVisible(true);
        });
    }
    
}
