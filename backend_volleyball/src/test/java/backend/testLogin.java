package backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class testLogin {
    String nom="testnom";
    String prenom="testprenom";
    String mdp="testmdp";

    @Test
    public void testajoutPerson(){
        int IdPerson=login.ajoutPerson(nom,prenom,mdp);
        assertEquals(nom,login.getNom(IdPerson));
    }

    @Test 
    public void testdeletePerson(){
        int IdPerson=login.ajoutPerson(nom,prenom,mdp);
       
        login.deletePerson(IdPerson);
        assertNotEquals(nom,login.getNom(IdPerson));
    }

}
