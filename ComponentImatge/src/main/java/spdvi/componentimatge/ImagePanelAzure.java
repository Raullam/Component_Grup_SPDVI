package spdvi.componentimatge;

import spdvi.serveis.AzureBlobService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import spdvi.logica.AvancarImatge;
import spdvi.logica.CarregarImatge;
import spdvi.logica.GuardarImatgeAzure;
import spdvi.logica.GuardarImatge;
import spdvi.logica.NetejarImatge;
import spdvi.logica.RedimensionarImatge;
import spdvi.logica.RetrocedirImatge;
import spdvi.logica.RotarImatge;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePanelAzure extends JFrame {

    private ImagePanel imagePanel;
    private JButton btnResize, btnClear, btnRotate, btnLeft, btnRight;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0; // Índice de la imagen actual
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=alejandrostorage1;AccountKey=lE5g6+hiDokS8nYgZ9RGcXexPo6wqGWMrho4IiKYEU+9CAJysciPs2q+VHDsoWQ41bfFMAcCmG+h+ASto4i3KQ==;EndpointSuffix=core.windows.net";
    private final String containerName = "images";
    private ArrayList<BufferedImage> bufferedImages = new ArrayList<>();
    private BufferedImage currentImage;
    private AzureBlobService blobService = new AzureBlobService(connectionString);

    public ImagePanelAzure() {
        setTitle("Adobad Photoshop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cargar y establecer el favicon
        setIconImage(loadIconImage("C:\\Users\\Rulox\\Downloads\\Nueva carpeta (67)\\ComponentNADAL\\ComponentImatge\\src\\main\\resources\\imatges\\2.png"));

        // Crear el panel de la imagen
        imagePanel = new ImagePanel();

        // Crear un JMenuBar
        JMenuBar menuBar = new JMenuBar();

        // Crear un JMenu para la navegación
        JMenu navigationMenu = new JMenu("Navegació");
        JMenu navigationMenu2 = new JMenu("Sobre noltros");

        JMenuItem itemNext2 = new JMenuItem("Conocenos!");
        itemNext2.addActionListener(e -> showAboutUs()); // Acción para mostrar la información en el mismo frame
        navigationMenu2.add(itemNext2);

        // Crear el submenú debajo de "Carregar Imatge pc"
        JMenu subMenuCarregar = new JMenu("Carregar");
        JMenuItem carregarImatgeLocal = new JMenuItem("Imatge local");
        JMenuItem carregarImatgeAzure = new JMenuItem("Imatge d'Azure");
        JMenuItem carregarGaleriaLocal = new JMenuItem("Galeria local");
        JMenuItem carregarGaleriaAzure = new JMenuItem("Galeria d'Azure");

        // Añadir las sub-opciones al submenú
        subMenuCarregar.add(carregarImatgeLocal);
        subMenuCarregar.add(carregarImatgeAzure);
        subMenuCarregar.add(carregarGaleriaLocal);
        subMenuCarregar.add(carregarGaleriaAzure);
        
        JMenu subMenuGuardar = new JMenu("Guardar");
        JMenuItem guardarImatgeLocal = new JMenuItem("Imatge local");
        JMenuItem guardarImatgeAzure = new JMenuItem("Imatge d'Azure");
        JMenuItem guardarGaleriaLocal = new JMenuItem("Galeria local");
        JMenuItem guardarGaleriaAzure = new JMenuItem("Galeria d'Azure");

        subMenuGuardar.add(guardarImatgeLocal);
        subMenuGuardar.add(guardarImatgeAzure);
        subMenuGuardar.add(guardarGaleriaLocal);
        subMenuGuardar.add(guardarGaleriaAzure);

        // Crear los botones
        btnResize = new JButton("Redimensionar");
        btnClear = new JButton("Limpiar Imagen");
        btnRotate = new JButton("Rotar Imagen");

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnResize);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRotate);

        // Agregar los JMenuItems al JMenu
        navigationMenu.add(subMenuCarregar); // Añadir el submenú debajo de "Carregar Imatge pc"
        navigationMenu.add(subMenuGuardar); // Añadir el submenú debajo de "Carregar Imatge pc"

        // Añadir componentes al frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        menuBar.add(navigationMenu);
        menuBar.add(navigationMenu2);

        // Crear paneles para los botones laterales
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        btnLeft = new JButton("<-");  // Botón izquierdo
        btnLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(Box.createVerticalGlue()); // Espaciado para centrar
        leftPanel.add(btnLeft);
        leftPanel.add(Box.createVerticalStrut(20)); // Separación entre botones
        leftPanel.add(Box.createVerticalGlue()); // Espaciado para centrar

        // Añadir el panel izquierdo al frame
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        btnRight = new JButton("->"); // Botón derecho
        btnRight.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalGlue()); // Espaciado para centrar
        rightPanel.add(btnRight);
        rightPanel.add(Box.createVerticalStrut(20)); // Separación entre botones
        rightPanel.add(Box.createVerticalGlue()); // Espaciado para centrar

        // Añadir el panel derecho al frame
        add(rightPanel, BorderLayout.EAST);

        // Establecer el JMenuBar en la ventana (JFrame)
        setJMenuBar(menuBar);

        // Añadir funcionalidad a los botones
        btnResize.addActionListener(e -> RedimensionarImatge.redimensionarImagen(imagePanel, this));
        btnClear.addActionListener(e -> NetejarImatge.limpiarImagen(imagePanel));
        btnRotate.addActionListener(e -> RotarImatge.rotarImagen(imagePanel));

        // Asignar acciones a los elementos del menú
        guardarImatgeLocal.addActionListener(e -> GuardarImatge.guardarImagenPC(imagePanel, this));
        guardarImatgeAzure.addActionListener(e -> GuardarImatgeAzure.selectAndSaveImage(currentImage, blobService, containerName));
        carregarGaleriaAzure.addActionListener(e -> CarregarImatge.cargarImagen(containerName, connectionString, bufferedImages, imagePaths, currentIndex, imagePanel));
        
        // Asignar las mismas acciones a los botones de los lados
        btnLeft.addActionListener(e -> AvancarImatge.mostrarSiguienteImagen(this)); // Retroceder
        btnRight.addActionListener(e -> RetrocedirImatge.mostrarImagenAnterior(this)); // Avanzar

        // Hacer que el frame escuche teclas
        setFocusable(true);
        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }

    // Método para mostrar la información "Conocenos!" en el mismo JFrame
    private void showAboutUs() {
        // Crear un panel para mostrar la información
        JPanel aboutUsPanel = new JPanel();
        aboutUsPanel.setLayout(new BoxLayout(aboutUsPanel, BoxLayout.Y_AXIS));

        // Cargar el icono que se usó de favicon
        Image iconImage = loadIconImage("C:\\Users\\Rulox\\Downloads\\Nueva carpeta (67)\\ComponentNADAL\\ComponentImatge\\src\\main\\resources\\imatges\\2.png");
        Image logoPau = loadIconImage("C:\\Users\\Rulox\\Downloads\\Nueva carpeta (67)\\ComponentNADAL\\ComponentImatge\\src\\main\\resources\\imatges\\paucasesnoves.png");

        // Crear el JLabel con el icono centrado
        JLabel iconLabel = new JLabel(new ImageIcon(iconImage));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        JLabel nomApp = new JLabel("Adobat Photoshop");
        nomApp.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado

        // Crear el JLabel con los nombres de los desarrolladores
        JLabel developersLabel = new JLabel("Desenvolupadors:");
        developersLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado

        // Crear un JLabel con los nombres de los desarrolladores
        JLabel dev1 = new JLabel("Alejandro Player");
        dev1.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        // Crear un JLabel con los nombres de los desarrolladores
        JLabel dev2 = new JLabel("Magi Aristondo");
        dev2.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        // Crear un JLabel con los nombres de los desarrolladores
        JLabel dev3 = new JLabel("Joan Miquel");
        dev3.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        // Crear un JLabel con los nombres de los desarrolladores
        JLabel dev4 = new JLabel("Raül Lama");
        dev4.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        JLabel cifp = new JLabel("CIFP PAU CASESNOVES INCA, MALLORCA.");
        cifp.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
        
         // Crear un botón debajo del icono CIFP
    JButton button = new JButton("Tornar al panell d'edició");
    button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado
    
    // Agregar un ActionListener al botón si es necesario
button.addActionListener(e -> goBackToImageEditingPanel());



     
// Redimensionar la imagen a un tamaño más pequeño (ejemplo: 50x50 píxeles)
Image scaledImage = logoPau.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Ajustar el tamaño

// Crear el JLabel con la imagen redimensionada
JLabel iconLabel2 = new JLabel(new ImageIcon(scaledImage));
iconLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centrado de la imagen
// Centrado

        // Añadir todo al panel
        aboutUsPanel.add(Box.createVerticalGlue());
        aboutUsPanel.add(iconLabel);       
        aboutUsPanel.add(nomApp);

        
        aboutUsPanel.add(Box.createVerticalStrut(20)); // Espaciado
        aboutUsPanel.add(developersLabel);
        aboutUsPanel.add(Box.createVerticalStrut(20)); // Espaciado

        aboutUsPanel.add(dev1);
        aboutUsPanel.add(dev2);
        aboutUsPanel.add(dev3);
        aboutUsPanel.add(dev4);
        aboutUsPanel.add(Box.createVerticalStrut(20)); // Espaciado
        
        aboutUsPanel.add(cifp);

        aboutUsPanel.add(iconLabel2);
        aboutUsPanel.add(Box.createVerticalGlue());
            aboutUsPanel.add(button); // Agregar el botón aquí


        // Reemplazar el contenido actual del JFrame con el panel de "Sobre Nosotros"
        getContentPane().removeAll(); // Limpiar el contenido actual
        getContentPane().add(aboutUsPanel, BorderLayout.CENTER); // Añadir el panel "Sobre Nosotros"
        revalidate(); // Revalidar el JFrame para aplicar los cambios
        repaint(); // Redibujar el JFrame
    }

    // Método para cargar el favicon
    private Image loadIconImage(String imagePath) {
        try {
            File iconFile = new File(imagePath);
            if (!iconFile.exists()) {
                System.out.println("Error: El archivo no existe en la ruta especificada: " + imagePath);
                return null;
            }
            BufferedImage iconImage = ImageIO.read(iconFile);
            if (iconImage == null) {
                System.out.println("Error: No se pudo leer la imagen. Asegúrese de que el formato sea compatible.");
            } else {
                return iconImage.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            System.out.println("Error al intentar leer la imagen: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
    
    private void goBackToImageEditingPanel() {
    // Crear un nuevo ImagePanelAzure, que puede ser la ventana principal de edición
    ImagePanelAzure newImagePanel = new ImagePanelAzure();
    
    // Cerrar el panel actual (sobre nosotros) y abrir el panel de edición
    newImagePanel.setVisible(true);
    this.setVisible(false);  // Ocultar la ventana actual
    this.dispose();  // Liberar recursos de la ventana anterior
}

}
