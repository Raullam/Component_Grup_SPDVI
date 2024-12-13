package spdvi.componentimatge;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

public class ImagePanelAzure extends JFrame {
    private ImagePanel imagePanel;
    private JButton btnLoad, btnResize, btnFiltre, btnGray, btnClear, btnRotate, btnSave, btnUpload, btnNext, btnPrevious;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0; // Índice de la imagen actual
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=alejandrostorage1;AccountKey=lE5g6+hiDokS8nYgZ9RGcXexPo6wqGWMrho4IiKYEU+9CAJysciPs2q+VHDsoWQ41bfFMAcCmG+h+ASto4i3KQ==;EndpointSuffix=core.windows.net";
    private final String containerName = "images";
    private BufferedImage currentImage;
    private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();
    AzureBlobService blobService = new AzureBlobService(connectionString);

    public ImagePanelAzure() {
        setTitle("Image Viewer Mejorado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel de la imagen
        imagePanel = new ImagePanel();

        // Crear los botones
        btnLoad = new JButton("Cargar Imagen");
        btnResize = new JButton("Redimensionar");
        btnFiltre = new JButton("Aplica Opacidad");
        btnGray = new JButton("Escala de Grises");
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
        buttonPanel.add(btnFiltre);
        buttonPanel.add(btnGray);
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
        btnLoad.addActionListener(e -> cargarImagen());
        btnResize.addActionListener(e -> redimensionarImagen());
        btnFiltre.addActionListener(e -> applyFilter(() -> applyOpacityFilter(0.5f)));
        btnGray.addActionListener(e -> applyFilter(this::applyGrayscaleFilter));
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

    private void cargarImagen() {
        BlobContainerClient containerClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(containerName);

        bufferedImages.clear();
        
        for (BlobItem blobItem : containerClient.listBlobs()) {
            try {
                BlobClient blobClient = containerClient.getBlobClient(blobItem.getName());
                byte[] imageData = blobClient.downloadContent().toBytes();
                ByteArrayInputStream imageStream = new ByteArrayInputStream(imageData);
                BufferedImage img = ImageIO.read(imageStream);

                if (img != null) {
                    bufferedImages.add(img);
                    imagePaths.add(blobItem.getName());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (!bufferedImages.isEmpty()) {
            currentIndex = 0;
            currentImage = bufferedImages.get(currentIndex);
            imagePanel.loadImage2(currentImage);
        }
    }

    private void applyFilter(Runnable filterAction) {
        if (currentImage != null) {
            try {
                filterAction.run();
                imagePanel.loadImage2(currentImage);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay ninguna imagen cargada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyOpacityFilter(float opacity) {
        if (opacity < 0.0f || opacity > 1.0f) {
            throw new IllegalArgumentException("La opacidad debe estar entre 0.0 y 1.0");
        }

        BufferedImage transparentImage = new BufferedImage(
                currentImage.getWidth(),
                currentImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = transparentImage.createGraphics();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(currentImage, 0, 0, null);
        g2d.dispose();

        currentImage = transparentImage;
    }

    private void applyGrayscaleFilter() {
        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                int rgb = currentImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int gray = (r + g + b) / 3;
                int newRgb = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                currentImage.setRGB(x, y, newRgb);
            }
        }
    }

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
        imagePanel.rotateImage(90);
    }

    private void guardarImagenPC() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String outputPath = fileChooser.getSelectedFile().getAbsolutePath();
            imagePanel.saveImage(outputPath);
        }
    }

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
        currentImage = null;
    }

    private void mostrarSiguienteImagen() {
        if (!bufferedImages.isEmpty()) {
            currentIndex = (currentIndex + 1) % bufferedImages.size();
            currentImage = bufferedImages.get(currentIndex);
            imagePanel.loadImage2(currentImage);
        }
    }

    private void mostrarImagenAnterior() {
        if (!bufferedImages.isEmpty()) {
            currentIndex = (currentIndex - 1 + bufferedImages.size()) % bufferedImages.size();
            currentImage = bufferedImages.get(currentIndex);
            imagePanel.loadImage2(currentImage);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImagePanelAzure frame = new ImagePanelAzure();
            frame.setVisible(true);
        });
    }
}
