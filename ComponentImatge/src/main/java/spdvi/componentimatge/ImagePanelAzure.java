package spdvi.componentimatge;

import spdvi.serveis.AzureBlobService;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import spdvi.logica.AvancarImatge;
import spdvi.logica.CarregarImatge;
import spdvi.logica.GuardarImatge;
import spdvi.logica.GuardarImatgeAzure;
import spdvi.logica.NetejarImatge;
import spdvi.logica.RedimensionarImatge;
import spdvi.logica.RetrocedirImatge;
import spdvi.logica.RotarImatge;
import spdvi.logica.MostrarDimensions;


public class ImagePanelAzure extends JFrame {
    private ImagePanel imagePanel;
    private JButton btnLoad, btnResize, btnClear, btnRotate, btnSave, btnUpload, btnNext, btnPrevious, btnShowDimensions;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0; // Índice de la imagen actual
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=alejandrostorage1;AccountKey=lE5g6+hiDokS8nYgZ9RGcXexPo6wqGWMrho4IiKYEU+9CAJysciPs2q+VHDsoWQ41bfFMAcCmG+h+ASto4i3KQ==;EndpointSuffix=core.windows.net";
    private final String containerName = "images";
    private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();
    private BufferedImage currentImage;
    private AzureBlobService blobService = new AzureBlobService(connectionString);   
    
    public ImagePanelAzure() {
        setTitle("Image Viewer Mejorado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel de la imagen
        imagePanel = new ImagePanel();

        // Crear los botones
        btnLoad = new JButton("Cargar Imagen");
        btnResize = new JButton("Redimensionar");
        btnShowDimensions = new JButton("Mostrar Dimensiones");

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
        buttonPanel.add(btnShowDimensions);
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
        btnResize.addActionListener(e -> RedimensionarImatge.redimensionarImagen(imagePanel,this));
        btnClear.addActionListener(e -> NetejarImatge.limpiarImagen(imagePanel));
        btnRotate.addActionListener(e -> RotarImatge.rotarImagen(imagePanel));
        btnSave.addActionListener(e -> GuardarImatge.guardarImagenPC(imagePanel,this));
        btnUpload.addActionListener(e -> GuardarImatgeAzure.selectAndSaveImage(currentImage, blobService, containerName));
        //btnNext.addActionListener(e -> AvancarImatge.mostrarSiguienteImagen(bufferedImages,currentIndex,imagePanel));
        //btnPrevious.addActionListener(e -> RetrocedirImatge.mostrarImagenAnterior(bufferedImages,currentIndex,imagePanel));
        btnShowDimensions.addActionListener(e -> MostrarDimensions.mostrarDimensions(imagePanel));

        btnNext.addActionListener(e -> AvancarImatge.mostrarSiguienteImagen(this));
        btnPrevious.addActionListener(e -> RetrocedirImatge.mostrarImagenAnterior(this));

        // Hacer que el frame escuche teclas
        setFocusable(true);
        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }
  // Métodos para obtener el índice y la lista de imágenes
    public int getCurrentIndex() {
        return currentIndex;
    }

    public ArrayList<BufferedImage> getBufferedImages() {
        return bufferedImages;
    }

    // Método para actualizar el índice y la imagen
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        this.currentImage = bufferedImages.get(index);
    }
    
    // Método para cargar una imagen
    public void setCurrentImage(BufferedImage image) {
        this.currentImage = image;
        imagePanel.loadImage(image); // Cargar imagen en el panel
        adjustHeightToImage(image);
    }

public void adjustHeightToImage(BufferedImage image) {
        if (image != null) {
            // Ajustar el alto del JFrame al tamaño de la imagen
            this.setSize(this.getWidth(), image.getHeight()); // Mantener el ancho y ajustar solo el alto
            this.setPreferredSize(new Dimension(this.getWidth(), image.getHeight())); // Establecer solo el alto
            this.revalidate();
            this.repaint();
        }
    }
}