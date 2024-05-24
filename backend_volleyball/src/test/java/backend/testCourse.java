package backend;


import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class testCourse {
    private final Course course= new Course();
    String  testId ="9999.56";
    String  testId1 ="9999.57";
    String  testId2 ="9999.58";
    String testString = "just test";
    String testUpdate="new data";
    String testTitle="test";
    int testChapitre= 1000;
    int testNewChapitre=10001;

    String insertID="1";
    int insertChapitre=1;
    String insertDescription="AIRE DE JEU \r\n "+"L’aire de jeu comprend le terrain de jeu et la zone libre. Elle doit être rectangulaire et symétrique.\r\n";
    String title="";
    @Test
    public void insert(){
        course.insert_course(insertID,title, insertDescription, insertChapitre);
    }
    @Test
    public void testInsert(){
        course.insert_course(testId, testTitle,testString, testChapitre);
    }
    @Test
    public void testDeleteDcimale(){
        assert course.delete_course_by_reference(testId);
     }
     @Test
    public void testDeleteIdChapitre(){
        assert course.delete_course(testChapitre);
     }
     @Test
    public void testDeleteString(){
       assert course.delete_course_by_description(insertDescription);
    }

    @Test
    public void testUpdateContext(){
        course.insert_course(testId,testTitle, testString, testChapitre);
        assert course.update_course(testId, testUpdate);
        assert  course.delete_course_by_reference(testId);
    }
   



    @Test
    public void testGetCoursesByChapterId(){
        course.insert_course(testId,testTitle, testString, testChapitre);
        course.insert_course(testId1,testTitle, testString, testChapitre);
        course.insert_course(testId2,testTitle, testString, testChapitre);
        List<Course> courseIds =course.getCoursesByChapterId(testChapitre);
        assertNotNull(courseIds); 
        System.out.println(courseIds);
        assert course.delete_course(testChapitre);
    }

    @Test
    public void testGetDescriptionByidCourse(){
        course.insert_course(testId, testTitle,testString, testChapitre);
        String description=course.getDescriptionByReferenceId(testId);
        assertNotNull(course); 
        System.out.println(description);
        assert course.delete_course_by_description(description);
       
    }
    @Test
    public void testSort_de_ligne(){
        course.insert_course(testId,testTitle, "jeter\n un nouveu ligne", testChapitre);
        String description=course.getDescriptionByReferenceId(testId);
        assertNotNull(course); 
        System.out.println(description);
        assert course.delete_course_by_description(description);
       
    }

    
}
       
