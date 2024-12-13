package spdvi.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CustomFontExample {
    public static void main(String[] args) {
        // Crear una finestra JFrame
        JFrame frame = new JFrame("Exemple de Font Personalitzada");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Crear un JLabel
        JLabel label = new JLabel("Text amb font personalitzada", SwingConstants.CENTER);

        try {
            // Carregar la font .otf
            File fontFile = new File("src/main/resources/fonts/FRESHFACE.ttf"); // Actualitza aquest camí
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // Ajustar la mida de la font
            customFont = customFont.deriveFont(40f); // Mida de la font en punts

            // Assignar la font al JLabel
            label.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            label.setText("No s'ha pogut carregar la font");
        }

        // Afegir el JLabel al JFrame
        frame.add(label);
        frame.setVisible(true);
    }
}