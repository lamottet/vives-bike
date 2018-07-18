package Test;

import databag.Fiets;
import databag.Lid;
import databag.Rit;
import databaseDB.FietsDB;
import databaseDB.LidDB;
import databaseDB.RitDB;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import datatype.Standplaats;
import datatype.Status;
import exception.ApplicationException;
import exception.DBException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Lamotte Tom
 */
public class RitDBTest {

  private Rit rit;
  private RitDB ritDB;
  private Lid lid;
  private LidDB lidDB;
  private Fiets fiets;
  private FietsDB fietsDB;

  public RitDBTest() {
    ritDB = new RitDB();
    lidDB = new LidDB();
    fietsDB = new FietsDB();
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws DBException, ApplicationException, Exception {
    // een lid met een rit maken
   
    lid = new Lid();
    lid.setVoornaam("Bart");
    lid.setNaam("Boos");
    lid.setEmail("boos@gmail.com");
    lid.setGeslacht(Geslacht.M);
    lid.setEindeDatumLidmaatschap(null);
    lid.setOpmerkingen("slechte betaler");
    lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
    lid.setTelNr("014145241");
    lid.setStartDatumLidmaatschap(LocalDate.now());
    
    //lid toevoegen aan db
    lidDB.toevoegenLid(lid);

    // rit maken
    rit = new Rit();
    rit.setRitID(1);
    rit.setStarttijd(LocalDateTime.now());
    rit.setEindtijd(null);
    rit.setFietsregistratienummer(1);
    rit.setRijksregisternummer(new Rijksregisternummer("80071636511"));
    rit.setPrijs(BigDecimal.ZERO);
    
    
    //fiets maken
    fiets = new Fiets();
    fiets.setStatus(Status.ACTIEF);
    fiets.setOpmerking(null);
    fiets.setStandplaats(Standplaats.TIELT);
    fiets.setRegistratienummer(1);
  }
  
  @Test
  public void testToevoegenRit() throws DBException, ApplicationException, Exception {
    try {
      // rit toevoegen
      ritDB.toevoegenRit(rit);
      // rit opnieuw ophalen
      Rit ophaalRit = ritDB.zoekRit(rit.getRitID());
       // toegevoegde rit vergelijken met opgehaalde rit
      assertEquals("FOUT: RitId niet correct", 1, ophaalRit.getRitID().toString());
      assertEquals("FOUT: Starttijd niet correct","12.00.00",ophaalRit.getStarttijd());
      assertEquals("FOUT: Eindtijd niet correct",null,ophaalRit.getEindtijd());
      assertEquals("FOUT: Bedrag niet correct",BigDecimal.ZERO,ophaalRit.getPrijs());
      assertEquals("FOUT: Fietsregistratienummer niet correct",1,ophaalRit.getFietsregistratienummer());
      assertEquals("FOUT: Rijksregisternummert niet correct","80071636511",ophaalRit.getRijksregisternummer());

     }catch(Exception e){ e.getMessage();
  }
  }

  // negatieve test: rit toevoegen zonder ritID
  @Test
  public void testToevoegenRitZonderRitID() throws DBException {
    thrown.expect(DBException.class);
    rit.setRitID(null);
    ritDB.toevoegenRit(rit);
    //fout toevoegen zonder ID
  }

  // negatieve test: rekening toevoegen zonder saldo
  @Test
  public void testToevoegenRitZonderRijksregisternummerLid() throws DBException {
    thrown.expect(DBException.class);
    rit.setRijksregisternummer(null);
    ritDB.toevoegenRit(rit);
    //fout geen rit toevoegen zonder rijksregisternummer
   
  }

  // negatieve test: rit toevoegen met zelfde ritID als reeds bestaande rit
  @Test
  public void testToevoegenRitZelfdeRitID() throws DBException, ApplicationException, Exception {
    thrown.expect(DBException.class);
    try {
      ritDB.toevoegenRit(rit);
      Rit nieuweRit = new Rit();
      nieuweRit.setRitID(1);
      nieuweRit.setPrijs(BigDecimal.ZERO);
      nieuweRit.setFietsregistratienummer(1);
      nieuweRit.setRijksregisternummer(new Rijksregisternummer("80071636511"));
      nieuweRit.setEindtijd(null);
      nieuweRit.setStarttijd(LocalDateTime.now());
      
      // rit toevoegen --> FOUT
      ritDB.toevoegenRit(nieuweRit);
    }finally{
  }
  }

 
  // negatieve test: rit wijzigen maar geen fietsregistratienummer opgeven
  @Test
  public void testWijzigenRitGeenFietsRegistratieNummer() throws DBException {
    thrown.expect(DBException.class);
    try {
      rit.setFietsregistratienummer(Integer.valueOf(null));
      ritDB.toevoegenRit(rit);
      // rit wijzigen --> FOUT
    }catch(DBException e){
        e.getMessage();
    }
  }
 
  
  //lege database
   @Test
  public void zoekAlleRitten() throws DBException, ApplicationException, Exception {
    try {
       ArrayList<Rit> ritten = ritDB.zoekAlleRitten();
      assertEquals("FOUT: aantal ritten niet correct", 0, ritten.size());
    } finally {
   }
  }
}
  
