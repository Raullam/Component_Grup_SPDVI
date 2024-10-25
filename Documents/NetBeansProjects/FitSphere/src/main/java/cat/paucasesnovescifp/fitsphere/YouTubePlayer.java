/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.paucasesnovescifp.fitsphere;

/**
 *
 * @author aleja
 */
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class YouTubePlayer extends JFrame {

    private JFXPanel jfxPanel;

    public YouTubePlayer() {
        setTitle("YouTube Player");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        jfxPanel = new JFXPanel(); // Crear el panel de JavaFX
        add(jfxPanel);

        // Inicializar JavaFX en un hilo separado
        Platform.runLater(() -> initFX(jfxPanel));

        setVisible(true);
    }

    private void initFX(JFXPanel panel) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // URL del video de YouTube
        String youtubeURL = "https://www.youtube.com/watch?v=jNQXAC9IVRw"; // Reemplaza VIDEO_ID con el ID del video

        // Cargar el video en el WebView
        webEngine.load(youtubeURL);

        // Crear la escena de JavaFX y añadir el WebView
        Scene scene = new Scene(webView, 800, 600);
        panel.setScene(scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new YouTubePlayer());
    }
}
