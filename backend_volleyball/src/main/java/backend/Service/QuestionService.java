package backend.Service;

import backend.Model.Question;
import backend.connectMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionService {

    public static List<Question> getQuestionsByChapter(int chapter) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM question WHERE chapitre = ? AND qst_qcm = 0 ORDER BY RAND() LIMIT 10";
        try (
                Connection connection = connectMysql.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, chapter);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question(resultSet.getInt("question_id"), resultSet.getInt("chapitre"), resultSet.getString("question_text"), resultSet.getInt("question_score"), resultSet.getString("reference_id"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions par chapitre : " + e.getMessage());
        }
        return questions;
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

    static class ChapterInfo {
        int chapter;
        int totalQuestions;

        ChapterInfo(int chapter, int totalQuestions) {
            this.chapter = chapter;
            this.totalQuestions = totalQuestions;
        }
    }

    public static List<Question> getRandomQuestions() {
        int totalQuestions = 40;
        List<Question> questions = new ArrayList<>();
        try (Connection connection = connectMysql.getConnection()) {
            // Step 1: Get the total number of questions per chapter
            String countSql = "SELECT chapitre, COUNT(*) AS total FROM question GROUP BY chapitre";
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ResultSet countResultSet = countStatement.executeQuery();

            // Step 2: Calculate the number of questions to select from each chapter
            List<ChapterInfo> chapterInfos = new ArrayList<>();
            int overallTotalQuestions = 0;
            while (countResultSet.next()) {
                int chapitre = countResultSet.getInt("chapitre");
                int chapterTotal = countResultSet.getInt("total");
                overallTotalQuestions += chapterTotal;
                chapterInfos.add(new ChapterInfo(chapitre, chapterTotal));
            }
            countResultSet.close();
            countStatement.close();

            // Step 3: Select questions based on proportions
            for (ChapterInfo chapterInfo : chapterInfos) {
                int chapterQuestionsToSelect = (int) Math.round((double) chapterInfo.totalQuestions / overallTotalQuestions * totalQuestions);
                String selectSql = "SELECT * FROM question WHERE chapitre = ? ORDER BY RAND() LIMIT ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectSql);
                selectStatement.setInt(1, chapterInfo.chapter);
                selectStatement.setInt(2, chapterQuestionsToSelect);
                ResultSet selectResultSet = selectStatement.executeQuery();
                while (selectResultSet.next()) {
                    Question question = new Question(
                            selectResultSet.getInt("question_id"),
                            selectResultSet.getInt("chapitre"),
                            selectResultSet.getString("question_text"),
                            selectResultSet.getInt("question_score"),
                            selectResultSet.getString("reference_id")

                    );
                    questions.add(question);
                }
                selectResultSet.close();
                selectStatement.close();
            }

            // Step 4: Shuffle the questions to ensure randomness
            Collections.shuffle(questions);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions : " + e.getMessage());
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

    public void insertQuestion( int chapitre, String questionText, int questionScore) {
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

    public void deleteQuestion( int questionId) {
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

    public void updateQuestion(int questionId, char chapitre, String questionText, int questionScore) {
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

}
