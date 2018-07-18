
package test;

import databag.Rit;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import transactie.RitTrans;

/**
 *
 * @author tom
 */
public class RitTransTest {
    private Rit rit;
    private RitTrans ritTrans;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
     
    public RitTransTest() {
        ritTrans = new RitTrans();
     
    }
    @Before
    public void setUp() throws Exception{
        // RIT aanmaken
        rit = new Rit();
        rit.setRitID(1);
        rit.setStarttijd(LocalDateTime.now());
        rit.setPrijs(BigDecimal.ONE);
        rit.setRijksregisternummer(new Rijksregisternummer("80071636511"));
        rit.setFietsregistratienummer(1);
    }
      
    //negatief testen ritID NUll
    @Test
        public void testToevoegenRitZonderID() throws  ApplicationException,Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Geen ritID ingevuld");
        
        //geen ritID
        rit.setRitID(null);
        //rit toevoegen ->FOUT
        ritTrans.toevoegenRit(rit);
    }
    
    //negatief testen starttijd NULL
    @Test
    public void testToevoegenRitZonderStarttijd() throws ApplicationException, Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Geen starttijd ingevuld");
        //geen starttijd
        rit.setStarttijd(null);
        //rit toevoegen -> FOUT
        ritTrans.toevoegenRit(rit);
    }
    
    //negatief testen invullen eindtijd
    @Test
    public void testToevoegenRitEindtijd() throws ApplicationException, Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Eindtijd is verboden");
        //eindtijd invullen
        rit.setEindtijd(LocalDateTime.now());
        //rit toevoegen -> FOUT
        ritTrans.toevoegenRit(rit);
    }
    
    //negatief testen: rijksregisternummer onbestaande
    @Test
    public void testToevoegenRitMetOnbestaandLid() throws ApplicationException, Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("lid bestaat niet,niet ingeschreven");
        //invullen lid
        rit.setRijksregisternummer(new Rijksregisternummer("40021200108"));
        //rit toevoegen -> FOUT
        ritTrans.toevoegenRit(rit);
    }
    
    //negatief testen: fietsregistratienummer onbestaande
    @Test
    public void testToevoegenRitMetOnbestaandeFiets() throws ApplicationException,Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Fiets bestaat niet ,foutief registratienummer");
        //invullen fietsregistratienummer
        rit.setFietsregistratienummer(100);
        //rit toevoegen -> FOUT
        ritTrans.toevoegenRit(rit);
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
                
        
        
        
        
        
    
 
