
package test;


import databag.Lid;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Lamotte Tom
 */
public class LidTest{
    
    Lid lid;
    @Before
    public void setUp() throws ApplicationException,Exception{
        try{
        lid = new Lid(new Rijksregisternummer("80071636511"),"Lamotte","Tom",Geslacht.M,"050707070","lamottetom@gmail.com",LocalDate.now(),"geen");
        }catch(ApplicationException ae){
            ae.getMessage();
        }
        
     }
    //wijzigen van rijksregisternummer
    //alle andere velden blijven ongewijzig.
    @Test
    public void testSetRijksregisternummer()throws ApplicationException,Exception {
        try{
        lid.setRijksregisternummer(new Rijksregisternummer("42110226849"));
        assertEquals("42110226849", lid.getRijksregisternummer());
        assertEquals(Geslacht.M, lid.getGeslacht());
        assertEquals("Lamotte",lid.getNaam());
        assertEquals("Tom",lid.getVoornaam());
        assertEquals("050707070",lid.getTelNr());
        assertEquals("lamottetom@gmail.com",lid.getOpmerkingen());
        assertEquals(LocalDate.now(), lid.getStartDatumLidmaatschap());
    }catch(ApplicationException ae){
        ae.getMessage();
     
        }finally{
            lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
        }
    }
        
        
    @Test
    public void testGetNaam(){
        assertEquals("80071636511", lid.getNaam());
    }
    
    @Test
    public void testAnderGeslacht() throws ApplicationException,Exception{
        try{
        Lid l = new Lid(new Rijksregisternummer("42110226849"),"Baets","Elke",Geslacht.V,"050727010","elkebaets@gmail.com",LocalDate.now(),"slechte betaler");
        l.setGeslacht(Geslacht.V);
        assertEquals(Geslacht.V, lid.getGeslacht());
        }catch(ApplicationException ae){
        ae.getMessage();
       }
    }
    
    @Test
    public void testGetDatum(){
        assertEquals(LocalDate.now(),lid.getStartDatumLidmaatschap());
    }
    
    public void testSetDatum(){
        lid.setStartDatumLidmaatschap(LocalDate.now());
        assertEquals(LocalDate.now(),lid.getStartDatumLidmaatschap());
    }
}


