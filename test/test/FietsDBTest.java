/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import databag.Fiets;
import databaseDB.FietsDB;
import datatype.Standplaats;
import datatype.Status;
import exception.DBException;
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
public class FietsDBTest {

    private Fiets fiets;
    private FietsDB fietsDB;

    public FietsDBTest(){
        fietsDB =new FietsDB();
    }
 
    @Rule
    public ExpectedException thrown
            = ExpectedException.none();

    @Before
    public void setUp() {
        // fiets maken
        fiets = new Fiets();
        fiets.setRegistratienummer(1);
        fiets.setStandplaats(Standplaats.KORTRIJK);
        fiets.setStatus(Status.ACTIEF);
        fiets.setOpmerking(null);
    }

    //een fiets toevoegen met alle velden
    @Test
    public void testToevoegenFietsMetRegistratienummer() throws DBException {
        try {
            // fiets toevoegen
            fiets.setRegistratienummer(fietsDB.toevoegenFiets(fiets));    
            // fiets opnieuw ophalen
            Fiets ophaalFiets = fietsDB.zoekFiets(fiets.getRegistratienummer());

            // toegevoegde fiets vergelijken met opgehaalde fiets
            assertEquals("FOUT: registratienummer niet correct", fiets.getRegistratienummer(), ophaalFiets.getRegistratienummer());
            assertEquals("FOUT: status niet correct", Status.ACTIEF, ophaalFiets.getStatus());
            assertEquals("FOUT: fietsstandplaats niet correct", Standplaats.KORTRIJK, ophaalFiets.getStandplaats());
        }catch(DBException e){
            e.getMessage();
        }
    }


    // twee fietsen toevoegen met hetzelde registratienummer
    @Test
    public void testToevoegenFietsZelfdeNummers() throws DBException {
        Integer fiets1 = null;
        Integer fiets2 = null;
        try {
            // fiets toevoegen
            fiets1 = fietsDB.toevoegenFiets(fiets);
            fiets.setRegistratienummer(fiets1);

            // fiets 2 maken
            Fiets f = new Fiets();
            f.setRegistratienummer(1);
            f.setStandplaats(Standplaats.KORTRIJK);
            f.setStatus(Status.ACTIEF);
            f.setOpmerking(null);
           

            // fiets2 toevoegen
            fiets2 = fietsDB.toevoegenFiets(f);

            // fiets2 opnieuw ophalen
            Fiets ophaalFiets = fietsDB.zoekFiets(f.getRegistratienummer());

            assertEquals("FOUT: registratienummer niet correct", fiets2, ophaalFiets.getRegistratienummer());
            //registernummer van de toegevoegde fiets2 is niet gelijk aan register van fiets
            assertFalse("FOUT: nummer niet correct", fiets1.equals(ophaalFiets.getRegistratienummer()));
        }catch(DBException e){
            e.getMessage();
        }
    }
    @Test
    public void testToevoegenFietsStatusActief() throws DBException {
        try {
            fiets.setStatus(Status.ACTIEF);
            // fiets toevoegen
            fiets.setRegistratienummer(fietsDB.toevoegenFiets(fiets));
            // fiets opnieuw ophalen
            Fiets ophaalFiets = fietsDB.zoekFiets(fiets.getRegistratienummer());
            // toegevoegde fiets vergelijken met opgehaalde fiets
            assertEquals("FOUT: registratienummer niet correct", fiets.getRegistratienummer(), ophaalFiets.getRegistratienummer());
            assertEquals("FOUT: status niet correct", Status.ACTIEF, ophaalFiets.getStatus());
            assertEquals("FOUT: standplaats niet correct", Standplaats.KORTRIJK, ophaalFiets.getStandplaats());
        }catch(DBException e){
            e.getMessage();
        }
    }
    // negatieve test: fiets toevoegen zonder registratienummer
    @Test
    public void testToevoegenFietsZonderRegistratienummer() throws DBException {
        thrown.expect(DBException.class);
        // geen registratienum
        fiets.setRegistratienummer(null);
        // fiets toevoegen --> FOUT
        fietsDB.toevoegenFiets(fiets);
    }

    // negatieve test: fiets toevoegen zonder standplaats
    @Test
    public void testToevoegenFietsZonderStandplaats() throws DBException {
        thrown.expect(DBException.class);
        //geen standplaats
        fiets.setStandplaats(null);
        // fiets toevoegen --> FOUT
        fietsDB.toevoegenFiets(fiets);
    }

    // negatieve test: fiets toevoegen zonder status
    @Test
    public void testToevoegenFietsZonderStatus() throws DBException {
        thrown.expect(DBException.class);
        
        fiets.setStatus(null);
        // fiets toevoegen --> FOUT
        fietsDB.toevoegenFiets(fiets);
    }

  
    @Test
    public void testToevoegenFietsNull() throws DBException {
        thrown.expect(DBException.class);
        Integer fietsNummer = fietsDB.toevoegenFiets(null);
        assertEquals("FOUT: fietsregistratienummer niet correct", null, fietsNummer);
    }

  
    @Test
    public void testWijzigenFietsToestand() throws DBException {
         try {
            // fiets toevoegen
            fiets.setRegistratienummer(fietsDB.toevoegenFiets(fiets));
            // fiets wijzigen
            fiets.setStatus(Status.IN_HERSTEL);
            fietsDB.wijzigenToestandFiets(1, Status.IN_HERSTEL);
            
            Fiets ophaalFiets = fietsDB.zoekFiets(fiets.getRegistratienummer());
            
            // wijzigingen vergelijken met opgehaalde fiets
            assertEquals("FOUT: fietsnummer niet correct", fiets.getRegistratienummer(), ophaalFiets.getRegistratienummer());
            assertEquals("FOUT: status niet correct", Status.IN_HERSTEL, ophaalFiets.getStatus());
            assertEquals("FOUT: Standplaats niet correct", Standplaats.KORTRIJK, ophaalFiets.getStandplaats());
         }catch(DBException e){
         e.getMessage();
         }
        
    }
}
    
    
    
   
