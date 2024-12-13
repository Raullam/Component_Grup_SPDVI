/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import spdvi.componentimatge.AzureBlobService;

/**
 *
 * @author Rulox
 */
public class GuardarImatgeAzure {
    
    public static void selectAndSaveImage(BufferedImage currentImage,AzureBlobService blobService, String containerName) {
        // Abrir el JFileChooser para seleccionar la imagen
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes JPG & PNG", "jpg", "png"));
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // Cargar la imagen seleccionada
                currentImage = ImageIO.read(selectedFile);

                // Obtener el nombre del archivo para usarlo como el blobName
                String fileName = selectedFile.getName(); // Nombre del archivo, como "imagen.jpg"
                String blobName = fileName; // Usar el nombre del archivo como blobName
                // O si prefieres asegurarte de que el nombre sea único, puedes agregar un prefijo único
                // String blobName = UUID.randomUUID().toString() + "_" + fileName;

                // Subir la imagen a Azure Blob Storage
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());  // Leer los bytes del archivo seleccionado
                blobService.uploadBlob(containerName, blobName, imageBytes);  // Subir los bytes al blob

                JOptionPane.showMessageDialog(null, "Imagen subida correctamente");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar o subir la imagen: " + e.getMessage());
            }
        }
    }
    
    
}
