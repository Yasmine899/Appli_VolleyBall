package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login {
    
    public static int ajoutPerson(String nom, String prenom, String mdp) {
        int idPerson = 0;
        String sql = "INSERT INTO login (nom, prenom, mdp) VALUES (?, ?, ?)";

        try (
            Connection connection = connectMysql.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, mdp);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPerson = generatedKeys.getInt(1);
                    }
                }
            } else {
                System.out.println("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Database error during the creation of a new person: " + e.getMessage());
            e.printStackTrace();
        }

        return idPerson;
    }

    public void updateMdp(int IdPerson, String newmdp) {
        String sql = "UPDATE login SET mdp = ? WHERE IdPerson = ?";
    
        try (Connection connection = connectMysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newmdp);
            statement.setInt(2, IdPerson);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Mot de passe mis à jour avec succès.");
            } else {
                System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la mise à jour du mot de passe: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void updatenom(int IdPerson, String newnom) {
        String sql = "UPDATE login SET nom = ? WHERE IdPerson = ?";
    
        try (Connection connection = connectMysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newnom);
            statement.setInt(2, IdPerson);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Nom mis à jour avec succès.");
            } else {
                System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la mise à jour du nom: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void updateprenom(int IdPerson, String newprenom) {
        String sql = "UPDATE login SET prenom = ? WHERE IdPerson = ?";
    
        try (Connection connection = connectMysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newprenom);
            statement.setInt(2, IdPerson);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Prénom mis à jour avec succès.");
            } else {
                System.out.println("Aucune mise à jour effectuée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la mise à jour du prénom: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public int getIdperson(String nom, String prenom, String mdp) {
        String sql = "SELECT IdPerson FROM login WHERE nom = ? AND prenom = ? AND mdp = ?";
        int idPerson = 0;
    
        try (Connection connection = connectMysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, mdp);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idPerson = resultSet.getInt("IdPerson");
                System.out.println("ID récupéré: " + idPerson);
            } else {
                System.out.println("Aucun utilisateur trouvé avec ces informations.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la récupération de l'ID: " + e.getMessage());
            e.printStackTrace();
        }
        return idPerson;
    }
    
    public static String getNom(int IdPerson) {
        String sql = "SELECT nom FROM login WHERE IdPerson = ?";
        String nom=null;
        try (
            Connection connection = connectMysql.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, IdPerson);  // Définir le paramètre IdPerson pour la requête

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nom = resultSet.getString("nom");  // Récupérer le nom
                System.out.println("Le nom de la personne est: " + nom);
                
            } else {
                nom="Aucun person trouvé pour ce IdPerson";
                System.out.println("Aucun enregistrement trouvé pour IdPerson: " + IdPerson);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la récupération du nom: " + e.getMessage());
            e.printStackTrace();
        }
        return nom;
        
    }

    public static void deletePerson(int IdPerson){
        String sql = "DELETE FROM login WHERE IdPerson = ?";

        try (
            Connection connection = connectMysql.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, IdPerson);  // Définir le paramètre IdPerson pour la requête

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("La personne a été supprimée avec succès.");
            } else {
                System.out.println("Aucune personne trouvée avec l'ID: " + IdPerson + " ou aucun changement effectué.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de base de données lors de la suppression de la personne: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
