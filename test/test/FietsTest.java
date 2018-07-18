/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import databag.Fiets;
import datatype.Standplaats;
import datatype.Status;
import exception.ApplicationException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lamotte Tom
 */
public class FietsTest {
    Fiets fiets;
    
    
    
    public FietsTest(){
    }
    
    @Before
    public void setUp() throws ApplicationException{
        fiets = new Fiets(1,Standplaats.BRUGGE,Status.ACTIEF,"geen");
       
     }
    //wijzigen van registratienummer
    //alle andere velden blijven ongewijzig.
    @Test
    public void testSetFietssregistratienummer(){
        fiets.setRegistratienummer(2);
        assertEquals(Integer.valueOf(2),fiets.getRegistratienummer());
        assertEquals(Standplaats.BRUGGE,fiets.getStandplaats());
        assertEquals(Status.ACTIEF,fiets.getStatus());
        assertEquals("geen",fiets.getOpmerking());
     
    }
    @Test
    public void testSetRegistratienummerNegatief(){
        fiets.setRegistratienummer(-2);
       
        assertEquals(Standplaats.BRUGGE, fiets.getStandplaats());
        assertEquals(Status.ACTIEF,fiets.getStatus());
        assertEquals("geen",fiets.getOpmerking());
        assertEquals(Integer.valueOf(-2),fiets.getRegistratienummer());
    }
            
        
        
    @Test
    public void testGetStandplaats(){
        assertEquals(Standplaats.BRUGGE, fiets.getStandplaats());
    }
    
    @Test
    public void testSetStandplaats(){
        fiets.setStandplaats(Standplaats.KORTRIJK);
        assertEquals(Standplaats.KORTRIJK,fiets.getStandplaats());
    }
    
    
    @Test
    public void testSetStatus(){
        fiets.setStatus(Status.IN_HERSTEL);
        assertEquals(Status.IN_HERSTEL,fiets.getStatus());
    }
    
    @Test
    public void testSetOpmerkingen(){
        fiets.setOpmerking("voorlicht stuk");
        assertEquals("voorlicht stuk",fiets.getOpmerking());
    }
  
    
}
