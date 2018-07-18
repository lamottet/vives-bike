
package test;


import databag.Lid;
import databag.Rit;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lamotte Tom
 */
public class RitTest {

    Rit rit;
    Lid lid;
    
    @Before
    public void setUp() throws ApplicationException,Exception{
   try{
        rit = new Rit(1,LocalDateTime.now(),BigDecimal.ONE,new Rijksregisternummer("80071636511"),1);
    
    }catch(ApplicationException a){
       a.getMessage();
}catch(Exception e){
    e.getMessage();
}}
    
    
    //negatief testen ritid =-1
    @Test
    public void testSetRitID()throws Exception{
        try{
            rit.setRitID(-2);
            assertEquals("FOUT:id niet correct",1,-2);
        }catch(Exception e){
            e.getMessage();
        }finally{
            rit.setRitID(1);
        }
    }
    
    //negatief testen prijs = -1
    @Test
    public void testSetRitPrijs() throws Exception{
        try{
            rit.setPrijs(BigDecimal.valueOf(-1));
            assertEquals("FOUT:prijs is negatief: ",1,rit.getPrijs());
    }catch(Exception e){
        e.getMessage();
    }finally{
            rit.setPrijs(BigDecimal.ONE);
    }
    }
    
    @Test
     public void testBestaatLid()throws Exception{
      try{
            //aanmaak lid
            Lid lid = new Lid();
            lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
            lid.setVoornaam("tom");
            lid.setNaam("lamotte");
            lid.setEindeDatumLidmaatschap(null);
            lid.setOpmerkingen("goede betaler");
            lid.setGeslacht(Geslacht.M);
            lid.setEmail("lamottetom@gmail.com");
            lid.setStartDatumLidmaatschap(LocalDate.now());
            lid.setTelNr("050715645");
            
            assertEquals("FOUT: geen lid: ",rit.getRijksregisternummer(),lid.getRijksregisternummer());
        }catch(Exception e){
            e.getMessage();
    }
   }
        

    
    //return fietsregistratienummer van rit
    @Test
    public void testGetFietsregistratienummer(){
        assertEquals("FOUT: geen fietsregistratienummer",rit.getFietsregistratienummer(),1);
    }
    
    @Test
    //er mag geen eindtijd zijn bij aanmaak rit
    public void testGeenEindtijd(){
        assertEquals("FOUT: er is een eindtijd",rit.getEindtijd(),null);
    }
    
   
}
        
    
   
    