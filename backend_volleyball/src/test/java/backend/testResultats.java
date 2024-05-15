package backend;

 
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testResultats {
    login login=new login();
    quizzs quizzs=new quizzs();
    int testIdQuizz=9999;
    int IdPerson;
    LocalDate localDate = LocalDate.now();
    int IdResultats;
    quizz_question quizz_question;

    @Before
    public void setUp() throws SQLException {
        //person
        
        String nom = "testnom";
        String prenom = "testprenom";
        String mdp = "testmdp";
        IdPerson = backend.login.ajoutPerson(nom, prenom, mdp);

        //quiz
        backend.quizzs.insert_quizz(testIdQuizz,1, 20);
        quizz_question = new quizz_question();
        quizz_question.insert_quizz_AllQuestion(testIdQuizz, 1);

        //resultats
        IdResultats=Resultat.commenceQuiz(testIdQuizz, IdPerson);
    }
    @After
    public void clean(){
        //person
        backend.login.deletePerson(IdPerson);

        //quiz
        quizzs.Delete_quizz(testIdQuizz);
        quizz_question.Delete_quizz_question(testIdQuizz);

        //resutats
        //pas encore
        
    }

    @Test
    public void testcommenceQuiz() throws SQLException{
        Resultat.afficherResultat(IdResultats);

    }

    @Test
    public void testsaveReponsesQuestion() throws SQLException{
        
        int IdReponseQuiz=Resultat.getIdReponseQuizByIdresultats(IdResultats);
        ArrayList<Integer> listIdQuestions=backend.quizzs.getIDQuestionbyQuizz(testIdQuizz);
        int IdQuestion=listIdQuestions.get(0);

        System.out.println("la premiere question:");
        System.out.println(Question.getQuestionById(IdQuestion).getQuestionText());

        ArrayList<String> reponseQuestion = new ArrayList<>(Arrays.asList("aaaa"));
        Resultat.saveReponsesQuestion(IdReponseQuiz, IdQuestion, reponseQuestion, 1);
        
        Resultat.afficherResultat(IdResultats);
    }


}
