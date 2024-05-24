package backend;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testQuizzsQuestion {
    quizz_question quizz_question=new quizz_question();
    quizzs quizzs =new quizzs();

  

    int testId =9999; 
    int testIdQuizz=999999;
    int testScoreFinal=20;
    int testIdChapitre=999999;

    
    
    @Before
    public void setup(){
       
        quizzs.insert_quizz(testIdQuizz,testIdChapitre,testScoreFinal);
       
    } 
    @After
    public void cleanAll(){
        quizzs.Delete_quizz(testIdQuizz);
    }
    @Test
    public void insertDonenw(){
        quizzs.insert_quizz(6, 2, 20);
        quizz_question.insert_quizz_AllQuestion(6, 2);
    }
    @Test
    public void testInsertDelete(){
     
        quizz_question.insert_quizz_question(testId,testIdChapitre, 1);
        assert quizz_question.Delete_quizz_question(testId);
    }
    @Test
    public void insert_quizz_AllQuestion(){
        quizz_question.insert_quizz_AllQuestion(testIdQuizz, 1);
        assert  quizz_question.Delete_quizz_questionByQuizz(testIdQuizz);
        }
}
