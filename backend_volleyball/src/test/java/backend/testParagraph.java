package backend;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testParagraph {
    BigDecimal  testIdCourse =new BigDecimal("9999.56");
    int testIdParagraph = 9999;
    int testIdParagraph2 = 10000;
    int testChapitre=9999;
    String testText="test paragraph \net saute de ligne";
    String testText2="test paragraph2222";
    CourseParagraph paragraph =new CourseParagraph();
    Course course =new Course();
    @Before
    public void setup(){
        course.insert_course(testIdCourse, null,testChapitre );
    }
    @After
    public void cleantout(){
        course.delete_course(testIdCourse);
    }
      @Test
    public void testInsert_delect(){

        paragraph.insertParagraph(testIdParagraph, testIdCourse, testText);   
        assert paragraph.delete_paragraph(testIdParagraph);
    }
    @Test 
    public void testInsert_delectByCourse(){

        paragraph.insertParagraph(testIdParagraph, testIdCourse, testText);   
        assert paragraph.delete_paragraph(testIdCourse);
    }

    @Test
    public void testGetContext(){
        paragraph.insertParagraph(testIdParagraph, testIdCourse, testText); 
        String resu= paragraph.getContext(testIdParagraph);
        assert resu!=null;
        System.out.println(resu);
       assert paragraph.delete_paragraph(testIdCourse);
    }
    @Test
    public void testGetIdParagraphsByIdCourse(){
        paragraph.insertParagraph(testIdParagraph, testIdCourse, testText);
        paragraph.insertParagraph(testIdParagraph2, testIdCourse, testText2);
        List<Integer> testlist=paragraph.getIdParametrebyIdCourse(testIdCourse);
        assert testlist.size()==2;
        paragraph.delete_paragraph(testIdCourse);
    }
}
