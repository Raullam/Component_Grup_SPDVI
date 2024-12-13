package spdvi.componentimatge;


import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.models.BlobItem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import spdvi.componentimatge.AzureBlobService;
import spdvi.componentimatge.ImagePanel;
import spdvi.logica.CarregarImatge;
/*TOMPARE NO TE NAS*/
public class ImagePanelAzure extends JFrame {
    private ImagePanel imagePanel;
    private JButton btnLoad, btnResize, btnClear, btnRotate, btnSave, btnUpload, btnNext, btnPrevious;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0; // Índice de la imagen actual
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=alejandrostorage1;AccountKey=lE5g6+hiDokS8nYgZ9RGcXexPo6wqGWMrho4IiKYEU+9CAJysciPs2q+VHDsoWQ41bfFMAcCmG+h+ASto4i3KQ==;EndpointSuffix=core.windows.net";
    private final String containerName = "images";
    
    public ImagePanelAzure() {
        setTitle("Image Viewer Mejorado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel de la imagen
        imagePanel = new ImagePanel();

        // Crear los botones
        btnLoad = new JButton("Cargar Imagen");
        btnResize = new JButton("Redimensionar");
        btnClear = new JButton("Limpiar Imagen");
        btnRotate = new JButton("Rotar Imagen");
        btnSave = new JButton("Guardar Imagen");
        btnUpload = new JButton("Pujar Imatge a Azure");
        btnNext = new JButton("Adelante");
        btnPrevious = new JButton("Atrás");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnResize);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRotate);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpload);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnPrevious);

        // Añadir componentes al frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        

        // Añadir funcionalidad a los botones
        btnLoad.addActionListener(e -> CarregarImatge.cargarImagen(containerName, connectionString, bufferedImages, imagePaths, currentIndex, imagePanel));
        btnResize.addActionListener(e -> redimensionarImagen());
        btnClear.addActionListener(e -> limpiarImagen());
        btnRotate.addActionListener(e -> rotarImagen());
        btnSave.addActionListener(e -> guardarImagenPC());
        btnUpload.addActionListener(e -> selectAndSaveImage());
        btnNext.addActionListener(e -> mostrarSiguienteImagen());
        btnPrevious.addActionListener(e -> mostrarImagenAnterior());

        // Hacer que el frame escuche teclas
        setFocusable(true);
        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }

    private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();


    private void redimensionarImagen() {
        String inputWidth = JOptionPane.showInputDialog(this, "Introduce el ancho:");
        String inputHeight = JOptionPane.showInputDialog(this, "Introduce la altura:");
        try {
            int width = Integer.parseInt(inputWidth);
            int height = Integer.parseInt(inputHeight);
            imagePanel.resizeImage(width, height);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void rotarImagen() {
        imagePanel.rotateImage(90); // Rotar 90 grados
    }

    private void guardarImagenPC() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String outputPath = fileChooser.getSelectedFile().getAbsolutePath();
            imagePanel.saveImage(outputPath);
        }
    }
    
    private BufferedImage currentImage;
    AzureBlobService blobService = new AzureBlobService(connectionString);

public void selectAndSaveImage() {
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
    
    private void limpiarImagen() {
        imagePanel.clearImage();
    }
    
    private void mostrarSiguienteImagen() {
    if (!bufferedImages.isEmpty()) {
        // Incrementar el índice y asegurarse de que no se desborde
        currentIndex = (currentIndex + 1) % bufferedImages.size();
        imagePanel.loadImage2(bufferedImages.get(currentIndex)); // Cargar la siguiente imagen
    }
}

private void mostrarImagenAnterior() {
    if (!bufferedImages.isEmpty()) {
        // Decrementar el índice y asegurarse de que no se desborde
        currentIndex = (currentIndex - 1 + bufferedImages.size()) % bufferedImages.size();
        imagePanel.loadImage2(bufferedImages.get(currentIndex)); // Cargar la imagen anterior
    }
}

    // Otros métodos (redimensionar, rotar, limpiar, etc.) se mantienen iguales

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImagePanelAzure frame = new ImagePanelAzure();
            frame.setVisible(true);
        });
    }
}
