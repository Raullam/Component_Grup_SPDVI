/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cat.paucasesnovescifp.jframes;

import cat.paucasesnovescifp.simulapinstructor.dataaccess.DataAccess;
import cat.paucasesnovescifp.simulapinstructor.models.Intent;
import cat.paucasesnovescifp.simulapinstructor.models.Review;
import cat.paucasesnovescifp.simulapinstructor.models.Usuari;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author aleja
 */
public class GestioUsuaris extends javax.swing.JFrame {

    /**
     * Creates new form Frame2
     */
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    DefaultTableModel dtm = new DefaultTableModel();
    DataAccess da = new DataAccess();
    private boolean dataChanged = false;
    private String nomInstructor;
    private boolean isPlaying = false;

    // Constructor que recibe el ID del instructor logueado
    public GestioUsuaris(String nomInstructor) {
        this();
        this.nomInstructor = nomInstructor;
    }
    
    public GestioUsuaris() {
        initComponents();

        // Configurar el JFrame
        setTitle("GestioUsuaris");
        setBounds(200, 100, 1100, 600); // Definir la posición y tamaño del JFrame
        setDefaultCloseOperation(GestioUsuaris.EXIT_ON_CLOSE);
        setLayout(null); // Usar null layout para colocar manualmente los componentes
        getContentPane().setBackground(Color.LIGHT_GRAY);
        
        txtAreaIntents.setText(da.getAttemptsPendingReviewText()); // cargar intentos pendientes de revision al txtArea

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent(); // media player para el video JPanel

        jTableUsuaris.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> { // listener de la tabla Usuaris
            if (!e.getValueIsAdjusting()) {
                // Obtener la fila seleccionada
                int selectedRow = jTableUsuaris.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener el ID del usuario de la fila seleccionada
                    int idUsuario = (int) jTableUsuaris.getValueAt(selectedRow, 0); // Asumiendo que el ID está en la primera columna

                    // Crear un objeto Usuari con el ID obtenido
                    Usuari selectedUser = new Usuari();
                    selectedUser.setId(idUsuario);

                    // Obtener los intentos del usuario seleccionado usando el método getAttemptsPerUser
                    ArrayList<Intent> intentos = da.getAttemptsPerUser(selectedUser);

                    // Establecer nombres de las columnas en jTableIntents
                    DefaultTableModel modelIntentos = (DefaultTableModel) jTableIntents.getModel();
                    modelIntentos.setColumnIdentifiers(new Object[]{
                        "ID Intent", "Nom Usuari", "Nom Exercici", "Inici", "VideoFile"
                    });

                    // Limpiar y actualizar la tabla con los datos de los intentos obtenidos
                    modelIntentos.setRowCount(0); // Limpiar las filas existentes
                    for (Intent intento : intentos) {
                        modelIntentos.addRow(new Object[]{
                            intento.getId(),
                            intento.getNomUsuari(),
                            intento.getNomExercici(),
                            intento.getTimestamp_Inici(),
                            intento.getVideofile()
                        });
                    }
                    // Seleccionar la primera fila de jTableIntents si hay filas en la tabla
                    if (jTableIntents.getRowCount() > 0) {
                        jTableIntents.setRowSelectionInterval(0, 0);

                        // Obtener el videofile de la primera fila y reproducirlo
                        String videoFile = (String) jTableIntents.getValueAt(0, 4); // Asumiendo que el VideoFile está en la columna 4
                        cargarYReproducirVideo(videoFile);
                    }
                }
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        BotonRead = new javax.swing.JButton();
        BotonRevision = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableUsuaris = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableIntents = new javax.swing.JTable();
        BotonDelete = new javax.swing.JButton();
        BotonUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaIntents = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtFieldValoracio = new javax.swing.JTextField();
        txtFieldComentari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BotonSignOut = new javax.swing.JButton();
        panelVideo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        BotonRevision1 = new javax.swing.JButton();
        BotonPause = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonRead.setText("Carregar Usuaris");
        BotonRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonReadActionPerformed(evt);
            }
        });

        BotonRevision.setText("Enviar Revisió");
        BotonRevision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRevisionActionPerformed(evt);
            }
        });

        jTableUsuaris.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableUsuaris);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Usuaris");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Intents de l'Usuari");

        jTableIntents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableIntents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableIntentsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableIntents);

        BotonDelete.setText("Eliminar");
        BotonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDeleteActionPerformed(evt);
            }
        });

        BotonUpdate.setText("Editar");
        BotonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonUpdateActionPerformed(evt);
            }
        });

        txtAreaIntents.setColumns(20);
        txtAreaIntents.setRows(5);
        txtAreaIntents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAreaIntentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaIntents);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Intents Pendents de Revisió");

        txtFieldValoracio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldValoracioActionPerformed(evt);
            }
        });

        jLabel4.setText("Valoracio:");

        jLabel5.setText("Comentari:");

        BotonSignOut.setText("Sign Out");
        BotonSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSignOutActionPerformed(evt);
            }
        });

        panelVideo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelVideo.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Videoplayer:");

        BotonRevision1.setText("Editar Revisió");
        BotonRevision1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRevision1ActionPerformed(evt);
            }
        });

        BotonPause.setText("Pause");
        BotonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonPauseActionPerformed(evt);
            }
        });

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu5.setText("About");

        jMenuItem1.setText("Alejandro");
        jMenu5.add(jMenuItem1);

        jMenuBar2.add(jMenu5);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BotonSignOut)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(BotonDelete)))
                                .addGap(62, 62, 62)
                                .addComponent(BotonUpdate))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(BotonRead, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BotonRevision)
                                .addGap(42, 42, 42)
                                .addComponent(BotonRevision1)
                                .addGap(68, 68, 68))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFieldComentari, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFieldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(106, 106, 106))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BotonPause, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFieldValoracio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtFieldComentari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BotonRevision1)
                                    .addComponent(BotonRevision))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BotonRead)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BotonDelete)
                                    .addComponent(BotonUpdate))
                                .addGap(36, 36, 36)
                                .addComponent(BotonSignOut))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonPause))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonReadActionPerformed
        // TODO add your handling code here:
        // Crear el modelo de la tabla para jTableReviews
        DefaultTableModel dtm2 = new DefaultTableModel();
        ArrayList<Usuari> usuaris = da.getAllUsers();

        // Añadir las columnas al modelo
        dtm2.addColumn("Id Usuari");
        dtm2.addColumn("Nom");
        dtm2.addColumn("Email");

        // Añadir filas al modelo
        for (Usuari u : usuaris) {
            dtm2.addRow(new Object[]{
                u.getId(),
                u.getNom(),
                u.getEmail()
            });
        }

        // Asignar el modelo a la JTable
        jTableUsuaris.setModel(dtm2);

    }//GEN-LAST:event_BotonReadActionPerformed

    private void BotonRevisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRevisionActionPerformed
        // TODO add your handling code here:
        // Obtener el ID del intento seleccionado desde el botón
        int idIntent = (int) BotonRevision.getClientProperty("idIntent");
        int valoracio = Integer.parseInt(txtFieldValoracio.getText());
        String comentari = txtFieldComentari.getText();

        // Validar que la valoración sea un número del 1 al 5
        try {
            if (valoracio < 1 || valoracio > 5) {
                JOptionPane.showMessageDialog(null, "La valoración debe ser un número entre 1 y 5.");
                return;
            }

            // Suponiendo que tienes el ID del reviewer (instructor que ha hecho login) en una variable
            int idReviewer = obtenerIdInstructorLogueado();

            // Crear el objeto Review y asignar los datos
            Review review = new Review();
            review.setIdIntent(idIntent);
            review.setIdReviewer(idReviewer);
            review.setValoracio(valoracio);
            review.setComentari(comentari);

            // Llamar a insertReview para insertar la review en la base de datos
            int result = da.insertReview(review);
            if (result > 0) {
                // Actualiza el JTextArea para eliminar la fila correspondiente
                txtAreaIntents.setText(da.getAttemptsPendingReviewText());
                JOptionPane.showMessageDialog(this, "Review insertada correctamente con ID: " + result);
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar la review.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La valoración debe ser un número válido.");
        }
    }//GEN-LAST:event_BotonRevisionActionPerformed

    private void BotonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTableUsuaris.getSelectedRow();
        
        if (selectedRow != -1) {  // Verifica si hay una fila seleccionada
            int id = (int) jTableUsuaris.getValueAt(selectedRow, 0); // Suponiendo que la columna 0 es 'Id'

            try (Connection con = da.getConnection()) {
                String query = "DELETE FROM usuaris WHERE Id = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    dtm.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Fila eliminada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la fila.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila.");
        }
    }//GEN-LAST:event_BotonDeleteActionPerformed

    private void BotonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonUpdateActionPerformed
        // TODO add your handling code here:
        ArrayList<Usuari> usuaris = da.getAllUsers();
        
        int selectedRow = jTableUsuaris.getSelectedRow(); // Obtiene la fila seleccionada

        if (selectedRow != -1) {  // Verifica si hay una fila seleccionada
            int id = (int) jTableUsuaris.getValueAt(selectedRow, 0);
            
            for (Usuari us : usuaris) {
                if (us.getId() == id) {
                    String Nom = (String) jTableUsuaris.getValueAt(selectedRow, 1);
                    String email = (String) jTableUsuaris.getValueAt(selectedRow, 2);

                    // Si detectamos cambios en 'NOM' o 'EMAIL'
                    if (!us.getNom().equals(Nom)) {
                        us.setNom(Nom);
                        dataChanged = true;
                    }
                    if (!us.getEmail().equals(email)) {
                        us.setEmail(email);
                        dataChanged = true;
                    }
                    if (dataChanged) {
                        da.updateUsuaris(us);
                    }
                    break;
                }
            }

            // Si no hubo cambios, muestra un mensaje
            if (!dataChanged) {
                JOptionPane.showMessageDialog(null, "No se han detectado cambios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila.");
        }
    }//GEN-LAST:event_BotonUpdateActionPerformed

    private void txtFieldValoracioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldValoracioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldValoracioActionPerformed

    private void txtAreaIntentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAreaIntentsMouseClicked
        // TODO add your handling code here:
        String selectedText = txtAreaIntents.getSelectedText();
        if (selectedText != null) {
            // Extrae el ID del intento seleccionado
            String[] columns = selectedText.split("\t");
            try {
                int idIntent = Integer.parseInt(columns[0]);  // Asumiendo que la primera columna es el ID del intento
                txtAreaIntents.setToolTipText("Intento seleccionado: " + idIntent);

                // Guarda el ID del intento para usarlo en la inserción
                BotonRevision.putClientProperty("idIntent", idIntent);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el ID del intento: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_txtAreaIntentsMouseClicked

    private void BotonSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSignOutActionPerformed
        // TODO add your handling code here:
        Login l = new Login();
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BotonSignOutActionPerformed

    private void jTableIntentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableIntentsMouseClicked
        // TODO add your handling code here:
        // Asegúrate de que se hace clic en una fila
        int selectedRow = jTableIntents.getSelectedRow();
        if (selectedRow != -1) {
            // Obtén el videofile del intento seleccionado
            String videoFile = (String) jTableIntents.getValueAt(selectedRow, 4); // Cambia a la posición correcta

            // Asegúrate de que el videoFile no esté vacío
            if (videoFile != null && !videoFile.isEmpty()) {
                cargarYReproducirVideo(videoFile);
            } else {
                JOptionPane.showMessageDialog(this, "El archivo de video no está disponible.");
            }
        }
    }//GEN-LAST:event_jTableIntentsMouseClicked

    private void BotonRevision1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRevision1ActionPerformed
        // TODO add your handling code here:
        Reviews r = new Reviews();
        r.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BotonRevision1ActionPerformed

    private void BotonPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonPauseActionPerformed
        // TODO add your handling code here:
        if (isPlaying) {
            mediaPlayerComponent.mediaPlayer().controls().pause();
            isPlaying = false;
            BotonPause.setText("Resume");
        } else {
            mediaPlayerComponent.mediaPlayer().controls().start();
            isPlaying = true;
            BotonPause.setText("Pause");
        }
    }//GEN-LAST:event_BotonPauseActionPerformed
    
    private void cargarYReproducirVideo(String videoFileName) {

        // Asegúrate de agregar una barra invertida entre la carpeta y el nombre del archivo
        String videoPath = "..\\FitSphere\\videos\\" + videoFileName;

        // Asegúrate de limpiar el panel antes de añadir un nuevo componente
        panelVideo.removeAll();
        panelVideo.add(mediaPlayerComponent, BorderLayout.CENTER);
        panelVideo.revalidate();
        panelVideo.repaint();

        // Cargar y reproducir el video
        mediaPlayerComponent.mediaPlayer().media().play(videoPath);
    }
    
    private int obtenerIdInstructorLogueado() {
        ArrayList<Usuari> instructors = da.getInstructors();
        
        for (Usuari instructor : instructors) {
            if (instructor.getNom().equals(nomInstructor)) {
                return instructor.getId();
            }
        }
        throw new IllegalStateException("Instructor logueado no encontrado");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestioUsuaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestioUsuaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestioUsuaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestioUsuaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GestioUsuaris().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonDelete;
    private javax.swing.JButton BotonPause;
    private javax.swing.JButton BotonRead;
    private javax.swing.JButton BotonRevision;
    private javax.swing.JButton BotonRevision1;
    private javax.swing.JButton BotonSignOut;
    private javax.swing.JButton BotonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableIntents;
    private javax.swing.JTable jTableUsuaris;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel panelVideo;
    private javax.swing.JTextArea txtAreaIntents;
    private javax.swing.JTextField txtFieldComentari;
    private javax.swing.JTextField txtFieldValoracio;
    // End of variables declaration//GEN-END:variables
}