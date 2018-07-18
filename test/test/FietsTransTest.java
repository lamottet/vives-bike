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
import exception.DBException;
import transactie.FietsTrans;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 *
 * @author Lamotte Tom
 */
public class FietsTransTest{
    

private Fiets fiets;
private FietsTrans fietsTrans;


@Rule
 public ExpectedException thrown
            = ExpectedException.none();


public FietsTransTest(){
    fietsTrans = new FietsTrans();
}
@Before
public void setUp(){
    //maken van een fietsobject
    fiets = new Fiets();
    fiets.setRegistratienummer(1);
    fiets.setStandplaats(Standplaats.KORTRIJK);
    fiets.setStatus(Status.ACTIEF);
    fiets.setOpmerking("bel stuk");
}


@Test
public void testToevoegenFietsMetRegistratienummer() throws Exception{
   
    //toevoegen van fiets
    fiets.setRegistratienummer(fietsTrans.toevoegenFiets(fiets));
    //toegevoegde fiet wordt gevonden
    assertEquals("FOUT; fietsregistratienummer niet correct",fiets.getRegistratienummer(),
            fietsTrans.zoekFiets(fiets.getRegistratienummer()));
   
}

//negatieve test: fiets toevoegen zonder registernummer
@Test
public void testToevoegenFietsZonderRegisternummer() throws Exception{ 
thrown.expect(Exception.class);
thrown.expectMessage("Registernummer moet ingegeven worden");

fiets.setRegistratienummer(null);
//FOUT bij fiets toevoegen
Integer fietsRegistratienummer = fietsTrans.toevoegenFiets(fiets);
     }

//negatieve test: fiets toevoegen zonder standplaats
@Test
public void testToevoegenFietsZonderStandplaats() throws Exception{
thrown.expect(Exception.class);
thrown.expectMessage("Standplaats niet ingevuld");

//geen standplaats
fiets.setStandplaats(null);
//FOUT bij fiets toevoegen
fiets.setRegistratienummer(fietsTrans.toevoegenFiets(fiets));
   }

//negatieve test: fiets zoeken zonder fietsregistratienummer
@Test
public void testZoekenFietsZonderRegistratienummer() throws Exception{

thrown.expect(Exception.class);
thrown.expectMessage("Fietsregistratienummer niet ingevuld");

fietsTrans.zoekFiets(null);
//Fout bij fiets zoeken
}


@Test
public void testZoekenFietsVerkeerdRegistratienummer()throws Exception{

thrown.expect(Exception.class);
thrown.expectMessage("registratienummer bestaat niet");
fietsTrans.zoekFiets(3);
//Fout bij fiets zoeken , bestaat niet
}


//negatief testen rit dat null is
@Test
public void testToevoegenFiets() throws DBException, Exception, ApplicationException{
thrown.expect(ApplicationException.class);
thrown.expectMessage("Er werd geen fiets ingegeven");

//toevoegen fiets
fietsTrans.toevoegenFiets(null);
}

//fietsstatus wijzigen
@Test
public void testWijzigenFietsStatusActiefUitOmloop() throws DBException, Exception,ApplicationException{
        thrown.expect(ApplicationException.class);
        thrown.expectMessage("fiets moet status ACTIEF krijgen");
        try {
            //fiets toevoegen
            fiets.setRegistratienummer(fietsTrans.toevoegenFiets(fiets));
           //fiets wijzigen
            fiets.setStatus(Status.UIT_OMLOOP);
            fietsTrans.wijzigenActiefNaarUitOmloop(fiets.getRegistratienummer());
        } finally {
          
        
        }
}
 // 
@Test
public void testFietsWijzigenOpmerking() throws DBException, Exception,ApplicationException{
thrown.expect(ApplicationException.class);
thrown.expectMessage("Opmerking niet ingevuld");
      try {
            // fiets toevoegen
           fietsTrans.toevoegenFiets(fiets);
           fiets.setOpmerking("");
           fietsTrans.wijzigenOpmerkingFiets(fiets.getRegistratienummer(), "");
       } finally {
            
        }
    }
}



































    

 
