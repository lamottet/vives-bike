package test;


import databag.Lid;
import databaseDB.LidDB;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.DBException;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author tom lamotte
 */
public class LidDBTest {

    private Lid lid;
    private LidDB lidDB;

    public LidDBTest(){
        lidDB =new LidDB();
    }
 
    @Rule
    public ExpectedException thrown
            = ExpectedException.none();

    @Before
    public void setUp()throws Exception {
        // lid maken
        lid = new Lid();
        lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
        lid.setStartDatumLidmaatschap(LocalDate.now());
        lid.setEmail("lamottetom@gmail.com");
        lid.setGeslacht(Geslacht.M);
        lid.setNaam("lamotte");
        lid.setVoornaam("tom");
        lid.setEindeDatumLidmaatschap(null);
        lid.setOpmerkingen("goede betaler");
        lid.setTelNr("050412345");
    }
        @Test
        public void testToevoegenLid() throws Exception {
        try {
            lidDB.toevoegenLid(lid);
            Lid ophaalLid = lidDB.zoekLid(lid.getRijksregisternummer());

            // toegevoegd lid vergelijken met opgehaald lid
            assertEquals("FOUT: lidRijksregisternummer niet correct", lid.getRijksregisternummer(), ophaalLid.getRijksregisternummer());
            assertEquals("FOUT: voornaam niet correct","Tom",ophaalLid.getVoornaam());
            assertEquals("FOUT: naam niet correct", "Lamotte", ophaalLid.getNaam());
            assertEquals("FOUT: Telefoon niet correct", "050412345",ophaalLid.getTelNr());
            assertEquals("FOUT: Geslacht niet correct",Geslacht.M,ophaalLid.getGeslacht());
            assertEquals("FOUT: startdatum niet correct",LocalDate.now(),ophaalLid.getStartDatumLidmaatschap());
            assertEquals("FOUT: einddatum niet correct",null,ophaalLid.getEindDatumLidmaatschap());
            assertEquals("FOUT: opmerking niet correct","goede betaler",ophaalLid.getOpmerkingen());
            assertEquals("FOUT: email niet correct","lamottetom@gmail.com",ophaalLid.getEmail());
        }catch(Exception e){
            e.getMessage();
        }
     }
   
    @Test 
    public void testToevoegenLidzonderRegisternummer() throws DBException {
          thrown.expect(DBException.class);
           lid.setRijksregisternummer(null);
           lidDB.toevoegenLid(lid);
           //toevoegen lid fout!!
    }
    
     // twee leden toevoegen met hetzelfde rijksregisternummer      
    @Test 
    public void testToevoegenTweeLedenZelfdeRijksregisters() throws DBException,Exception{
    
        try{
            //lid toevoegen
            lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
            lidDB.toevoegenLid(lid);
                // lid 2 maken
            Lid lid2 = new Lid();
            lid2.setRijksregisternummer(new Rijksregisternummer("80032000133"));
            lid2.setNaam("De Baere");
            lid2.setVoornaam("Elke");
            lid2.setGeslacht(Geslacht.V);
            lid2.setEmail("debaere@gmail.com");
            lid2.setOpmerkingen(null);
            lid2.setEindeDatumLidmaatschap(null);
            lid2.setStartDatumLidmaatschap(LocalDate.now());
            lid2.setTelNr("050714547");
            //lid 2 toevoegen in db
            lidDB.toevoegenLid(lid2);
            //lid 2 ophalen uit db
            Lid ophaalLid = lidDB.zoekLid(lid2.getRijksregisternummer());
            //registernummer van de toegevoegde fiets2 is niet gelijk aan register van fiets
            assertFalse("FOUT: Rijksregisternummer mag niet gelijk zijn: ", lid.getRijksregisternummer().equals(ophaalLid.getRijksregisternummer()));
        }catch(DBException e){
            e.getMessage();
        }
  
    }

    // negatieve test: lid toevoegen zonder naam
    @Test
    public void testToevoegenLidZonderNaam() throws DBException {
        thrown.expect(DBException.class);
        
        lid.setNaam(null);
        // lid toevoegen --> FOUT
        lidDB.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen zonder startdatum
    @Test
    public void testToevoegenLidZonderStartdatum() throws DBException {
        thrown.expect(DBException.class);
        //geen startdatum
        lid.setStartDatumLidmaatschap(null);
        // lid toevoegen --> FOUT
        lidDB.toevoegenLid(lid);
    }

    // negatieve test: fiets toevoegen zonder status
    @Test
    public void testToevoegenLidZonderVoornaam() throws DBException {
        thrown.expect(DBException.class);
        lid.setVoornaam(null);
        // lid toevoegen --> FOUT
        lidDB.toevoegenLid(lid);
    }
 
    @Test
    public void testWijzigenLidToestand() throws DBException,Exception{
         try {
            // lid toevoegen
            lidDB.toevoegenLid(lid);
            //toestand lid wijzigen
            lid.setEindeDatumLidmaatschap(LocalDate.now());
            lidDB.toevoegenLid(lid);
            Lid ophaalLid = lidDB.zoekLid(lid.getRijksregisternummer());
            // wijzigingen vergelijken met opgehaald lid
            assertEquals("FOUT: rijksregisternummer niet correct", lid.getRijksregisternummer(), ophaalLid.getRijksregisternummer());
            assertEquals("FOUT: status niet correct",lid.getEindDatumLidmaatschap(),ophaalLid.getEindDatumLidmaatschap());
            
         }catch(DBException e){
         e.getMessage();}
        
    }
   @Test
    public void testWijzigenLidZonderNaam() throws DBException {
        thrown.expect(DBException.class);
        // lid toevoegen
        lidDB.toevoegenLid(lid);
        try {
            // lid wijzigen --> FOUT
            lid.setNaam(null);
            lidDB.wijzigenLid(lid);

        } finally {
           
        }
    }
}