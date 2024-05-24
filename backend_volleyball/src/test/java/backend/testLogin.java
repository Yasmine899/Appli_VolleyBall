package backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testLogin {
    String nom="aaaaaa";
    String prenom="testprenom";
    String mdp="testmdp";
    int IdPerson;

    @Before
    public void setup(){
        IdPerson=login.ajoutPerson(nom,prenom,mdp);
    }

    @After
    public void clean(){
        login.deletePerson(IdPerson);
    }

    @Test
    public void testajoutPerson(){
        
        assertEquals(nom,login.getNom(IdPerson));
    }

    @Test 
    public void testdeletePerson(){
        login.deletePerson(IdPerson);
        assertNotEquals(nom,login.getNom(IdPerson));
    }

}
