package backend;

import java.util.List;

import org.junit.Test;

public class testQuizzs {
    private final quizzs quizzs=new quizzs();
    int testIdQuizz=9999;
    int testIdQuizz2=9998;
    int testIdQuizz3=9997;
    int testIdChapitre=9999;
    int testIdChapitre2=9998;
    int testScoreFinal=9999;
    

    @Test 
    public void testGetQuizzbyidquizz(){
      
        System.out.println(  quizzs.getQuestionbyQuizz(1));
    }
     @Test
    public void testInsert(){
        quizzs.insert_quizz(testIdQuizz,1, 20);
        quizz_question quizz_question = new quizz_question();
        quizz_question.insert_quizz_AllQuestion(testIdQuizz, 1);
    }
    @Test
    public void testDeletebyid(){
      
       quizz_question quizz_question = new quizz_question();
       assert quizz_question.Delete_quizz_question(testIdQuizz);
       assert quizzs.Delete_quizz(testIdQuizz);
    }
    @Test
    public void testGetQuizzbyidChapitre(){
        quizzs.insert_quizz(testIdQuizz,testIdChapitre, testScoreFinal);
        quizzs.insert_quizz(testIdQuizz2,testIdChapitre, testScoreFinal);
        quizzs.insert_quizz(testIdQuizz3,testIdChapitre, testScoreFinal);
        List<Integer>test_List=quizzs.getQuizzbyidChapitre(testIdChapitre);
        assert (test_List.size()==3);
        System.out.println(test_List);
        quizzs.Delete_quizz(testIdQuizz);
        quizzs.Delete_quizz(testIdQuizz2);
        quizzs.Delete_quizz(testIdQuizz3);
    }
    @Test
    public void testGetRandomQuizz(){
        quizzs.insert_quizz(testIdQuizz,testIdChapitre, testScoreFinal);
        quizzs.insert_quizz(testIdQuizz2,testIdChapitre2, testScoreFinal);
        quizzs.insert_quizz(testIdQuizz3,testIdChapitre, testScoreFinal);
        int testId=quizzs.getRandomQuizz(testIdChapitre);
        List<Integer> list = List.of(testIdQuizz,testIdQuizz3);
        assert (list.contains(testId));
        System.out.println(testId);
        quizzs.Delete_quizz(testIdQuizz);
        quizzs.Delete_quizz(testIdQuizz2);
        quizzs.Delete_quizz(testIdQuizz3);
    }

    @Test
    public void testGetScoreTotalByIdQuizz(){
        quizzs.insert_quizz(testIdQuizz,testIdChapitre, testScoreFinal);
        int score=quizzs.getScoreTotalByIdQuizz(testIdQuizz);
        System.out.println(score);
        assert score==testScoreFinal;
        quizzs.Delete_quizz(testIdQuizz);
    }

}
