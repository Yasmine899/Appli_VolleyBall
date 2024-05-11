package backend;

import org.junit.Test;

public class testExamen {
    private final examen examen=new examen();
 
    int testexamen=999;
     @Test
    public void testInsert(){
        examen.insert_new_examen(testexamen);
    }
    @Test
    public void testDelete(){
        examen.Delete_examen(testexamen);
    }
    @Test
    public void testGetExamenByid(){
        examen.insert_new_examen(testexamen);
        System.out.println(examen.getQuestionbyid(testexamen));
        examen.Delete_examen(testexamen);
    }
}
