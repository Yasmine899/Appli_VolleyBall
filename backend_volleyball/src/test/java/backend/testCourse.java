package backend;


import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

public class testCourse {
    private final Course course= new Course();
    BigDecimal  testId =new BigDecimal("9999.56");
    BigDecimal  testId1 =new BigDecimal("9999.57");
    BigDecimal  testId2 =new BigDecimal("9999.58");
    String testString = "just test";
    String testUpdate="new data";
    int testChapitre= 1000;
    int testNewChapitre=10001;
    @Test
    public void testInsert(){
        course.insert_course(testId, testString, testChapitre);
    }
    @Test
    public void testDeleteDcimale(){
        assert course.delete_course(testId);
     }
     @Test
    public void testDeleteIdChapitre(){
        assert course.delete_course(testChapitre);
     }
     @Test
    public void testDeleteString(){
       assert course.delete_course(testString);
    }
    @Test
    public void testInsert_delete(){
        course.insert_course(testId, testString, testChapitre);
        assert course.delete_course(testString);
    }
    @Test
    public void testUpdateContext(){
        course.insert_course(testId, testString, testChapitre);
        assert course.update_course(testId, testUpdate);
        assert  course.delete_course(testId);
    }
    @Test
    public void testUpdateChaptre(){
        course.insert_course(testId, testString, testChapitre);
        assert course.update_course(testId, testNewChapitre);
        assert  course.delete_course(testId);
    }



    @Test
    public void testGetCoursesByChapterId(){
        course.insert_course(testId, testString, testChapitre);
        course.insert_course(testId1, testString, testChapitre);
        course.insert_course(testId2, testString, testChapitre);
        List<BigDecimal> courseIds =course.getCoursesByChapterId(testChapitre);
        assertNotNull(courseIds); 
        System.out.println(courseIds);
       
    }

    @Test
    public void testGetDescriptionByidCourse(){
        course.insert_course(testId, testString, testChapitre);
        String description=course.getDescriptionByidCourse(testId);
        assertNotNull(course); 
        System.out.println(description);
        assert course.delete_course(description);
       
    }
    @Test
    public void testSort_de_ligne(){
        course.insert_course(testId, "jeter\n un nouveu ligne", testChapitre);
        String description=course.getDescriptionByidCourse(testId);
        assertNotNull(course); 
        System.out.println(description);
        assert course.delete_course(description);
       
    }

    
}
       
