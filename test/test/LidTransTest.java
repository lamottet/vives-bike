/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import databag.Lid;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import transactie.LidTrans;

/**
 *
 * @author Lamotte Tom
 */
public class LidTransTest {
   
    private Lid lid;
    private LidTrans lidTrans;

    @Rule
    public ExpectedException thrown
            = ExpectedException.none();

    public LidTransTest() {
        lidTrans = new LidTrans();
    }

    @Before
    public void setUp() throws Exception{
        // lid aanmaken
        lid = new Lid();
        lid.setRijksregisternummer(new Rijksregisternummer("80071636511"));
        lid.setNaam("Verhelst");
        lid.setVoornaam("Peter");
        lid.getStartDatumLidmaatschap();
        lid.setEmail("verhelstp@gmail.com");
        lid.setTelNr("050142457");
        lid.setGeslacht(Geslacht.M);
        lid.setOpmerkingen("slechte betaler");
    }
 
    @Test
    public void testToevoegenLidZonderRijksregisternummer() throws Exception {
        try {
            // lid toevoegen
           Lid l = new Lid();
           l.setRijksregisternummer(new Rijksregisternummer(null));
           lidTrans.toevoegenLid(l);
            // toegevoegde lid wordt teruggevonden
            assertEquals("FOUT: Rijksregisternummer niet ingevuld", l.getRijksregisternummer(), lidTrans.zoekLid(l.getRijksregisternummer())); 
            // lid weer verwijderen
            lidTrans.uitschrijvenLid(l.getRijksregisternummer());
        }catch(Exception e){
            e.getMessage();
        }
        
    }


    // negatieve test: lid toevoegen zonder voornaam
    @Test
    public void testToevoegenLidGeenVoornaam() throws Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Voornaam niet ingevuld");
        //geen voornaam
        lid.setVoornaam(null);
        // lid toevoegen --> FOUT
        lidTrans.toevoegenLid(lid);

    }

    // negatieve test: lid toevoegen zonder naam
    @Test
    public void testToevoegenLidGeenNaam() throws Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Naam niet ingevuld");
        //geen naam
        lid.setNaam(null);
        // lid toevoegen --> FOUT
        lidTrans.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen zonder telefoonnummer
       public void testToevoegenLidZonderFoon() throws Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Postcode niet ingevuld");
        //geen telnr
        lid.setTelNr(null);
        // lid toevoegen --> FOUT
        lidTrans.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen zonder
    @Test
    public void testToevoegenLidZonderGeslacht() throws Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Geslacht niet ingevuld");

        //geen geslacht
        lid.setGeslacht(null);
        // lid toevoegen --> FOUT
       lidTrans.toevoegenLid(lid);
    }

    // negatieve test: Lid toevoegen met ongeldige naam (lege string)
    @Test
    public void testToevoegenLidOngeldigeNaam() throws Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Naam niet ingevuld");
        //geen geldige naam
        lid.setNaam("     ");
        // lid toevoegen --> FOUT
        lidTrans.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen met ongeldig email (lege string)
    @Test
    public void testToevoegenLidOngeldigEmail() throws Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Email niet ingevuld");
        //geen geldige mail
        lid.setEmail("");
        // lid toevoegen --> FOUT
        lidTrans.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen die reeds uitgeschreven is
    @Test
    public void testToevoegenLidOngeldigeStatusUitgeschreven() throws Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("lid moet status INGESCHREVEN krijgen");

        //status uitgeschreven
        lid.setEindeDatumLidmaatschap(LocalDate.now());
        // lid toevoegen
        lidTrans.toevoegenLid(lid);
    }

    // negatieve test: lid toevoegen die al bestaat 
    @Test
    public void testToevoegenLidNietUniek() throws Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("bestaat al");

        // lid 1 maken
        Lid l = new Lid();
        l.setRijksregisternummer(new Rijksregisternummer("80071636511"));
        l.setNaam("Bral");
        l.setVoornaam("Kris");
        l.setGeslacht(Geslacht.M);
        l.setEmail("bral@gmail.com");
        l.setStartDatumLidmaatschap(LocalDate.now());
        l.setTelNr("050415417");
        l.setOpmerkingen("geen");
        
        try {
            // lid toevoegen
            lidTrans.toevoegenLid(l);
            // tweede (zelfde)lid toevoegen --> FOUT
            lidTrans.toevoegenLid(l);
        } finally {
            // lid weer verwijderen
           lidTrans.uitschrijvenLid(l.getRijksregisternummer());
        }
    }


    // negatieve test: lid toevoegen die null is
    @Test
    public void testToevoegenLidNull() throws Exception {
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("Er werd geen lid opgegeven");

        // lid toevoegen
        lidTrans.toevoegenLid(null);

    }

    // negatieve test: lid wijzigen die niet bestaat
    @Test
    public void testWijzigenLidBestaatNiet() throws Exception{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("lid niet gevonden.");
        try {
            // onbestaand lid
            Lid l = new Lid();
            l.setRijksregisternummer(new Rijksregisternummer("42110226511"));
            // onbestaand lid wijzigen
            lidTrans.wijzigenLid(l);
            lidTrans.uitschrijvenLid(l.getRijksregisternummer());
        }finally{

      }
    }
}