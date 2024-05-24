package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private int questionId;
    private int chapitre;
    private String questionText;
    private int questionScore;


    public int getScore(){
        return this.questionScore;
    }
    public Question(int chapitre, String questionText, int questionScore) {
        this.chapitre = chapitre;
        this.questionText = questionText;
        this.questionScore = questionScore;
    }

    public Question() {
        
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChapitre() {
        return chapitre;
    }

    public void setChapitre(int chapitre) {
        this.chapitre = chapitre;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", chapitre=" + chapitre +
                ", questionText='" + questionText + '\'' +
                ", questionScore=" + questionScore +
                '}';
    }
    public void insertQuestion() {
        String sql = "INSERT INTO question (chapitre, question_text, question_score) VALUES (?, ?, ?)";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, chapitre);
            statement.setString(2, questionText);
            statement.setInt(3, questionScore);
            statement.executeUpdate();
            System.out.println("Question insérée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion de la question : " + e.getMessage());
        }
    }

    public void deleteQuestion() {
        String sql = "DELETE FROM question WHERE question_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = ((Connection) connection).prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Question supprimée avec succès !");
            } else {
                System.out.println("Aucune question correspondante trouvée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la question : " + e.getMessage());
        }
    }

    public void updateQuestion() {
        String sql = "UPDATE question SET chapitre = ?, question_text = ?, question_score = ? WHERE question_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, chapitre);
            statement.setString(2, questionText);
            statement.setInt(3, questionScore);
            statement.setInt(4, questionId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Question mise à jour avec succès !");
            } else {
                System.out.println("Aucune question correspondante trouvée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la question : " + e.getMessage());
        }
    }

    public static List<Question> getQuestionsByChapter(int chapter) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question WHERE chapitre = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, chapter);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setChapitre(resultSet.getInt("chapitre"));
                    question.setQuestionText(resultSet.getString("question_text"));
                    question.setQuestionScore(resultSet.getInt("question_score"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions par chapitre : " + e.getMessage());
        }
        return questions;
    }

    public static Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM question WHERE question_id = ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setChapitre(resultSet.getInt("chapitre"));
                    question.setQuestionText(resultSet.getString("question_text"));
                    question.setQuestionScore(resultSet.getInt("question_score"));
                    return question;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la question par ID : " + e.getMessage());
        }
        return null;
    }

    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionId(resultSet.getInt("question_id"));
                question.setChapitre(resultSet.getInt("chapitre"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setQuestionScore(resultSet.getInt("question_score"));
                questions.add(question);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de toutes les questions : " + e.getMessage());
        }
        return questions;
    }

    public static int countQuestions() {
        String sql = "SELECT COUNT(*) AS total FROM question";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du comptage des questions : " + e.getMessage());
        }
        return 0;
    }

    public boolean validateQuestion() {
        if (questionText == null || questionText.isEmpty()) {
            System.out.println("Le texte de la question est vide !");
            return false;
        }
        if (questionScore <= 0) {
            System.out.println("Le score de la question n'est pas valide !");
            return false;
        }
        return true;
    }

    public static List<Question> searchQuestions(String keyword) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question WHERE question_text LIKE ?";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setChapitre(resultSet.getInt("chapitre"));
                    question.setQuestionText(resultSet.getString("question_text"));
                    question.setQuestionScore(resultSet.getInt("question_score"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des questions : " + e.getMessage());
        }
        return questions;
    }


}
