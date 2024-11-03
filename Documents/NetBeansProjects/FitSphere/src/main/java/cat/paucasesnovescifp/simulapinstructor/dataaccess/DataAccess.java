package cat.paucasesnovescifp.simulapinstructor.dataaccess;

import cat.paucasesnovescifp.simulapinstructor.models.Intent;
import cat.paucasesnovescifp.simulapinstructor.models.Review;
import cat.paucasesnovescifp.simulapinstructor.models.Usuari;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel
 */
public class DataAccess {

    public Connection getConnection() {
        Connection connection = null;
        
        // Cadena de conexión corregida
        String connectionString = "jdbc:sqlserver://localhost;database=simulapdb;trustServerCertificate=true;user=sa;password=1234;";
       
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return connection;
    }
    
    /*
    public ArrayList<Usuari> getUsuaris() {
        ArrayList<Usuari> usuaris = new ArrayList<>(); // mos cream s'objecte
        String sql = "select * from usuaris"; // feim sa query
        
        Connection connection = getConnection(); // mos connectam
        try {
            PreparedStatement selectStatement = connection.prepareStatement(sql); // preparam la query y la enviamos al servidor
            ResultSet resultSet = selectStatement.executeQuery();
            
            while (resultSet.next()) {
            Usuari user = new Usuari(
            resultSet.getInt("Id"),
            resultSet.getString("Nom"),
          resultSet.getString("Email"),
        resultSet.getString("PasswordHash"),
     resultSet.getBoolean("IsInstructor")
    );
    usuaris.add(user);
}
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuaris;
    }*/


    // Consigue todos los usuarios según el estado de instructor
public ArrayList<Usuari> getUsersByInstructorStatus(boolean isInstructor) {
    ArrayList<Usuari> usuaris = new ArrayList<>();
    String sql = "select * from Usuaris WHERE IsInstructor=?";
    
    try (Connection con = getConnection(); 
         PreparedStatement selectStatement = con.prepareStatement(sql)) {

        selectStatement.setBoolean(1, isInstructor);
        ResultSet resultSet = selectStatement.executeQuery();

        while (resultSet.next()) {
            Usuari user = new Usuari();
            user.setId(resultSet.getInt("Id"));
            user.setNom(resultSet.getString("Nom"));
            user.setEmail(resultSet.getString("Email"));
            user.setPasswordHash(resultSet.getString("PasswordHash"));
            user.setInstructor(resultSet.getBoolean("IsInstructor"));
            usuaris.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return usuaris;
}

// Método para conseguir todos los usuarios que no son instructores
public ArrayList<Usuari> getAllUsers() {
    return getUsersByInstructorStatus(false);
}

// Método para conseguir todos los instructores
public ArrayList<Usuari> getInstructors() {
    return getUsersByInstructorStatus(true);
}

    // METODO PARA REGISTRAR USUARIO
    public int registerUser(Usuari u) {
        String sql = "INSERT INTO dbo.Usuaris (Nom, Email, PasswordHash, IsInstructor)"
                + " VALUES (?,?,?,?)";
        try (Connection con = getConnection(); 
             PreparedStatement insertStatement = con.prepareStatement(sql)) {
            insertStatement.setString(1, u.getNom());
            insertStatement.setString(2, u.getEmail());
            insertStatement.setString(3, u.getPasswordHash());
            insertStatement.setBoolean(4, u.getInstructor());

            insertStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Método para actualizar los cambios en la base de datos
public void updateUsuaris(Usuari usuari) {
    String query = "UPDATE usuaris SET Nom = ?, Email = ? WHERE Id = ?";

    try (Connection con = getConnection()) {
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, usuari.getNom());
        stmt.setString(2, usuari.getEmail());
        stmt.setInt(3, usuari.getId());

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public ArrayList<Intent> getAttemptsPendingReview() {
    ArrayList<Intent> intents = new ArrayList<>();
    String sql = "SELECT Intents.Id, Intents.IdUsuari, Intents.IdExercici"
               + " FROM Intents"
               + " WHERE Intents.Id NOT IN (SELECT IdIntent FROM Review)" // subquery para los intentos que no esten en la tabla Reviews
               + " ORDER BY Timestamp_Inici";
    try (Connection connection = getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(sql)) {

        ResultSet resultSet = selectStatement.executeQuery();
        while (resultSet.next()) {
            Intent attempt = new Intent();
            attempt.setId(resultSet.getInt("Id"));
            attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
            attempt.setIdExercici(resultSet.getInt("IdExercici"));
            intents.add(attempt);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return intents;
}
    
    // Método adicional para filtrar intentos por usuario
public ArrayList<Intent> getAttemptsPendingReviewByUser(int idUsuario) {
    ArrayList<Intent> allAttempts = getAttemptsPendingReview();
    ArrayList<Intent> userAttempts = new ArrayList<>();

    // Filtrar los intentos del usuario seleccionado
    for (Intent intento : allAttempts) {
        if (intento.getIdUsuari() == idUsuario) {
            userAttempts.add(intento);
        }
    }
    return userAttempts;
}

public String getAttemptsPendingReviewText() {
    StringBuilder text = new StringBuilder();
    text.append("ID Intent\tID Usuari\tID Exercici\n"); // Agrega los nombres de las columnas

    for (Intent intento : getAttemptsPendingReview()) {
        text.append(intento.getId()).append("\t")
            .append(intento.getIdUsuari()).append("\t")
            .append(intento.getIdExercici()).append("\n");
    }
    return text.toString();
}

    public int insertReview(Review r) {
        int result = 0;
        String sql = "INSERT INTO dbo.Review (IdIntent, IdReviewer, Valoracio, Comentari)"
                + " VALUES (?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, r.getIdIntent());
            insertStatement.setInt(2, r.getIdReviewer());
            insertStatement.setInt(3, r.getValoracio());
            insertStatement.setString(4, r.getComentari());

            result = insertStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Creating review failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long longResult = generatedKeys.getLong(1);
                    result = longResult.intValue();
                } else {
                    throw new SQLException("Creating review failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Mètode per comprovar si un intent es la repetició de un exercici
     * 'failed'. Comprova si ja existeix un intent amb el mateix IdUsuari i
     * IdExercici i la \n data es anterior a la de intent.
     *
     * @param intent El intent a comprovar
     * @return el id del intent anterior o 0 si no existeix un intent anterior.
     */
    public int getPreviousFailedAttempt(Intent intent) {
        return 0;
    }

    public ArrayList<Intent> getAttemptsPerUser(Usuari user) {
        ArrayList<Intent> intents = new ArrayList<>();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id"
                + " WHERE Intents.IdUsuari=?"
                + " ORDER BY Intents.IdExercici";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, user.getId());
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Intent attempt = new Intent();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
                attempt.setNomUsuari(resultSet.getString("Nom"));
                attempt.setIdExercici(resultSet.getInt("IdExercici"));
                attempt.setNomExercici(resultSet.getString("NomExercici"));
                attempt.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                attempt.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                attempt.setVideofile(resultSet.getString("VideoFile"));
                intents.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intents;

    }

    public Review getAttemptReview(int idIntent) {
        Review review = null;
        String sql = "SELECT * FROM Review WHERE IdIntent = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, idIntent);
            ResultSet resultSet = selectStatement.executeQuery();
            review = new Review();
            while (resultSet.next()) {
                review.setId(resultSet.getInt("Id"));
                review.setIdIntent(resultSet.getInt("IdIntent"));
                review.setIdReviewer(resultSet.getInt("IdReviewer"));
                review.setValoracio(resultSet.getInt("Valoracio"));
                review.setComentari(resultSet.getString("Comentari"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }
    
    public int updateReview(Review r) {
        int result = 0;
        String sql = "UPDATE Review SET Valoracio=?, Comentari=? WHERE Id=?";
        try (Connection conn = getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            updateStatement.setInt(3, r.getId());
            updateStatement.setInt(1, r.getValoracio());
            updateStatement.setString(2, r.getComentari());

            result = updateStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Updating review failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getMaxID() {
    String sql = "SELECT MAX(id) FROM usuaris"; // Consulta SQL para obtener el ID máximo
    int maxId = -1; // Valor por defecto en caso de que no se encuentre ningún ID

    // Intentar obtener la conexión y ejecutar la consulta
    try (Connection connection = getConnection(); 
         PreparedStatement selectStatement = connection.prepareStatement(sql);
         ResultSet resultSet = selectStatement.executeQuery()) { // Ejecutar la consulta

        if (resultSet.next()) { // Comprobar si hay resultados
            maxId = resultSet.getInt(1); // Obtener el primer valor del resultado (ID máximo)
        }
    } catch (SQLException ex) {
        Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex); // Manejo de excepciones
    }

    return maxId;
}

}
