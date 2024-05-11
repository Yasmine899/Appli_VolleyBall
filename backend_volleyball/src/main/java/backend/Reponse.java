package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reponse {
    private int reponseId;
    private int questionId;
    private String reponseText;
    private boolean reponseCorrecte;


    public void insertReponse() {
        String sql = "INSERT INTO reponse (question_id, reponse_text, reponse_correcte) VALUES (?, ?, ?)";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            statement.setString(2, reponseText);
            statement.setBoolean(3, reponseCorrecte);
            statement.executeUpdate();
            System.out.println("Réponse insérée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la réponse : " + e.getMessage());
        }
    }

    public void deleteReponse() {
        String sql = "DELETE FROM reponse WHERE reponse_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, reponseId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réponse supprimée avec succès !");
            } else {
                System.out.println("Aucune réponse correspondante trouvée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réponse : " + e.getMessage());
        }
    }

    public void updateReponse() {
        String sql = "UPDATE reponse SET question_id = ?, reponse_text = ?, reponse_correcte = ? WHERE reponse_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            statement.setString(2, reponseText);
            statement.setBoolean(3, reponseCorrecte);
            statement.setInt(4, reponseId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Réponse mise à jour avec succès !");
            } else {
                System.out.println("Aucune réponse correspondante trouvée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la réponse : " + e.getMessage());
        }
    }


    public static Reponse getReponseById(int reponseId) {
        String sql = "SELECT * FROM reponse WHERE reponse_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, reponseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Reponse reponse = new Reponse();
                    reponse.setReponseId(resultSet.getInt("reponse_id"));
                    reponse.setQuestionId(resultSet.getInt("question_id"));
                    reponse.setReponseText(resultSet.getString("reponse_text"));
                    reponse.setReponseCorrecte(resultSet.getBoolean("reponse_correcte"));
                    return reponse;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la réponse par ID : " + e.getMessage());
        }
        return null;
    }

    public static List<Reponse> getReponsesByQuestionId(int questionId) {
        List<Reponse> reponses = new ArrayList<>();
        String sql = "SELECT * FROM reponse WHERE question_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reponse reponse = new Reponse();
                    reponse.setReponseId(resultSet.getInt("reponse_id"));
                    reponse.setQuestionId(resultSet.getInt("question_id"));
                    reponse.setReponseText(resultSet.getString("reponse_text"));
                    reponse.setReponseCorrecte(resultSet.getBoolean("reponse_correcte"));
                    reponses.add(reponse);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réponses par ID de question : " + e.getMessage());
        }
        return reponses;
    }

    public static int countReponses() {
        String sql = "SELECT COUNT(*) AS total FROM reponse";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du comptage des réponses : " + e.getMessage());
        }
        return 0;
    }

    public static List<Reponse> getCorrectReponsesByQuestionId(int questionId) {
        List<Reponse> correctReponses = new ArrayList<>();
        String sql = "SELECT * FROM reponse WHERE question_id = ? AND reponse_correcte = 1";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reponse reponse = new Reponse();
                    reponse.setReponseId(resultSet.getInt("reponse_id"));
                    reponse.setQuestionId(resultSet.getInt("question_id"));
                    reponse.setReponseText(resultSet.getString("reponse_text"));
                    reponse.setReponseCorrecte(resultSet.getBoolean("reponse_correcte"));
                    correctReponses.add(reponse);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réponses correctes par ID de question : " + e.getMessage());
        }
        return correctReponses;
    }

    public static List<Reponse> getIncorrectReponsesByQuestionId(int questionId) {
        List<Reponse> incorrectReponses = new ArrayList<>();
        String sql = "SELECT * FROM reponse WHERE question_id = ? AND reponse_correcte = 0";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reponse reponse = new Reponse();
                    reponse.setReponseId(resultSet.getInt("reponse_id"));
                    reponse.setQuestionId(resultSet.getInt("question_id"));
                    reponse.setReponseText(resultSet.getString("reponse_text"));
                    reponse.setReponseCorrecte(resultSet.getBoolean("reponse_correcte"));
                    incorrectReponses.add(reponse);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réponses incorrectes par ID de question : " + e.getMessage());
        }
        return incorrectReponses;
    }

    public static void deleteReponsesByQuestionId(int questionId) {
        String sql = "DELETE FROM reponse WHERE question_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " réponses ont été supprimées pour la question avec l'ID : " + questionId);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des réponses par ID de question : " + e.getMessage());
        }
    }

    public void setReponseId(int reponseId) {
        this.reponseId = reponseId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setReponseText(String reponseText) {
        this.reponseText = reponseText;
    }

    public void setReponseCorrecte(boolean reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }
}
