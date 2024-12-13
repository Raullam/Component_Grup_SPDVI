/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.componentimatge;


/**
 *
 * @author aleja
 */
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AzureBlobService {
    private final BlobServiceClient blobServiceClient;

    // Constructor para inicializar el cliente de Blob Storage
    public AzureBlobService(String connectionString) {
        this.blobServiceClient = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();
    }

    // Método para cargar la imagen a Azure Blob Storage
    public void uploadBlob(String containerName, String blobName, byte[] imageBytes) {
        // Obtener el contenedor de blobs
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        // Obtener el cliente del blob
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        // Subir la imagen directamente desde el array de bytes
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes)) {
            // Subir el contenido de los bytes al blob
            blobClient.upload(byteArrayInputStream, imageBytes.length, true);

            // Opcional: Establecer un encabezado HTTP para el tipo de contenido
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType("image/jpeg");
            blobClient.setHttpHeaders(headers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}