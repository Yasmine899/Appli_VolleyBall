package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMysql {

    public Connection getConnection() {
        //url incorrecte
        String url = "jdbc:mysql://yzeng.insa-toulouse.fr:3306/bdd_pir_volleyball";
        String username = "bdd_pir_volleyball";
        String password = "bdd_pir_03";
        try {
            return DriverManager.getConnection(url, username, password);
            
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        return null;
    }
}


