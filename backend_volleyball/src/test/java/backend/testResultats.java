package backend;

import static org.junit.Assert.assertEquals;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testResultats {
    int testIdQuizz=1;
    int IdPerson;
    LocalDate localDate = LocalDate.now();
    int IdResultats;

    @Before
    public void setUp() throws SQLException {
        //person
        
        String nom = "testnom";
        String prenom = "testprenom";
        String mdp = "testmdp";
        IdPerson = backend.login.ajoutPerson(nom, prenom, mdp);

      

        //resultats
        IdResultats=Resultat.commenceQuiz(testIdQuizz, IdPerson);
    }
    @After
    public void clean() throws SQLException{
         //resutats
         Resultat.deleteResultat(IdResultats);
        //person
        backend.login.deletePerson(IdPerson);

       
        
    }

    @Test
    public void testcommenceQuiz() throws SQLException{
        Resultat.afficherResultat(IdResultats);

    }

    @Test
    public void testsaveReponsesQuestion() throws SQLException{
        System.out.println("--avant enregistrer les reponse : ");
        Resultat.afficherResultat(IdResultats);
        
        int IdReponseQuiz=Resultat.getIdReponseQuizByIdresultats(IdResultats);
        ArrayList<Integer> listIdQuestions=backend.quizzs.getIDQuestionbyQuizz(testIdQuizz);
        int IdQuestion=listIdQuestions.get(0);

        System.out.println("--je veux enregistrer la premiere question:");
        System.out.println(Question.getQuestionById(IdQuestion).getQuestionText());

        ArrayList<String> reponseQuestion = new ArrayList<>(Arrays.asList("aaaa"));
        Resultat.saveReponsesQuestion(IdReponseQuiz, IdQuestion, reponseQuestion, 1);
        
        System.out.println("--apres enregistrer les reponse : ");
        Resultat.updateNoteQuiz(IdResultats);
        Resultat.afficherResultat(IdResultats);
     
        assertEquals(1,Resultat.getNoteQuiz(IdResultats));
    }


}
