/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.logica;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import spdvi.componentimatge.ImagePanel;

/**
 *
 * @author Rulox
 */
public class CarregarImatge {
    
    public static void cargarImagen(String containerName,String connectionString,ArrayList<BufferedImage> bufferedImages, ArrayList<String> imagePaths, int currentIndex,ImagePanel imagePanel) {
    BlobContainerClient containerClient = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient()
            .getBlobContainerClient(containerName);

    bufferedImages.clear(); // Limpia las imágenes cargadas previamente

    // Iterar sobre los blobs en el contenedor
    for (BlobItem blobItem : containerClient.listBlobs()) {
        try {
            BlobClient blobClient = containerClient.getBlobClient(blobItem.getName());
            // Descargar el contenido del blob como un array de bytes
            byte[] imageData = blobClient.downloadContent().toBytes();

            // Convertir el array de bytes en un BufferedImage
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageData);
            BufferedImage img = ImageIO.read(imageStream); // Lee la imagen desde el flujo de bytes

            if (img != null) {
                bufferedImages.add(img); // Agrega la imagen a la lista
                imagePaths.add(blobItem.getName()); // Agrega el nombre del archivo si es necesario
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Asegúrate de que haya imágenes en la lista
    if (!bufferedImages.isEmpty()) {
        currentIndex = 0; // Inicializa al primer índice
        imagePanel.loadImage2(bufferedImages.get(currentIndex)); // Cargar la primera imagen
    }
}

}
