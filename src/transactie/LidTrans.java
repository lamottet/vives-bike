package transactie;

import databag.Lid;
import databag.Rit;
import databaseDB.LidDB;
import exception.ApplicationException;
import java.util.ArrayList;

/**
 *
 * @author Lamotte Tom
 */

public class LidTrans implements InterfaceLidTrans{
    /**
     * Voegt een lid toe.
     * 
     * @param l die moet worden toegevoegd
     * @throws exception.ApplicationException
     * @throws Exception wanneer er geen fiets werd opgegeven, wanneer niet alle velden (correct) zijn ingevuld,
     * wanneer er al een lid bestaat of wanneer er fouten zijn uit de db
     */
    
    @Override
    public void toevoegenLid (Lid l) throws ApplicationException,Exception{
            //parameter ingevuld?
            if(l == null){
                 throw new ApplicationException("Er werd geen lid opgegeven.");
            }
            
            //zijn alle gegevens van het lid ingevuld?
            checkVeldenIngevuld(l);
            
            //bestaat het lid?
            if(zoekLid(l.getRijksregisternummer()) != null){
                 throw new ApplicationException("Het lid bestaat reeds");
                
            }
            //toevoegen
            LidDB ldb = new LidDB();
            ldb.toevoegenLid(l);
    }
    /**
     * Wijzigt van een lid de velden rijksregisternummer, naam, voornaam, geslacht, 
     * telefoonnummer,email,startlidmaatschap, eindelidmaatschap,opmerkingen
     * 
     * @param teWijzigenLid nieuwe gegevens van een lid die moetengebruikt worden
     * @throws Exception wanneer er geen parameter is ingevuld, wanneer niet alle
     * velden (correct) werden ingevuld,
     */
    
    @Override
    public void wijzigenLid(Lid teWijzigenLid) throws Exception{
    //parameter ingevuld?
    if(teWijzigenLid == null){
        throw new ApplicationException(" Geen lid opgegeven om te wijzigen");
    }
    //zijn alle gegevens ingevuld?
    checkVeldenIngevuld(teWijzigenLid);
    
    // zit het lid die gewijzigd moet worden in database?
    Lid wijzigenLid = zoekLid(teWijzigenLid.getRijksregisternummer());
    if(wijzigenLid == null){
        throw new ApplicationException("Lid niet gevonden");
    }
  
    //lid wijzigen
    LidDB ldb  = new LidDB();
    ldb.wijzigenLid(teWijzigenLid);
}
/**
 * Controleren of alle velden van Lid (behalve eindelidmaatschap) zijn ingevuld.
 * 
 * @throws Exception bij naam niet ingevuld, voornaam niet ingevuld, geslacht niet ingevuld, telefoon
 * niet ingevuld, email niet ingevuld, startdatum niet ingevuld
 */


private void checkVeldenIngevuld(Lid l) throws Exception{
    if(l.getRijksregisternummer()== null||l.getRijksregisternummer().trim().equals("")){
        throw new ApplicationException("Rijksregisternummer niet ingevuld");
    }
    
    if(l.getNaam()==null||l.getNaam().trim().equals("")){
        throw new ApplicationException("Naam niet ingevuld");
       
    }
    if(l.getVoornaam()==null||l.getVoornaam().trim().equals("")){
        throw new ApplicationException("Voornaam niet ingevuld");
    }
    
    if(l.getGeslacht() == null){
         throw new ApplicationException("Geslacht niet ingevuld");
    }
    if(l.getTelNr() == null){
         throw new ApplicationException("Telefoon niet ingewuld");
    }
    if(l.getEmail()==null|l.getEmail().trim().equals("")){
        throw new ApplicationException("Email niet ingevuld");
    }
    if(l.getStartDatumLidmaatschap()==null){
        throw new ApplicationException("Startdatum niet ingevuld");
    }
}

/**
 * 
 * @param rr
 * @throws Exception 
     * @throws exception.ApplicationException 
 */
    
    @Override
    public void uitschrijvenLid(String rr) throws Exception{
        //parameter ingevuld?
        if(rr ==null){
            throw new ApplicationException("Er werd geen rijksregisternummer ingevoerd");
        }
       
         // bestaat het lid?
        Lid l = zoekLid(rr);
        if(l == null){
            throw new ApplicationException("lid is niet gevonden");
        }
        
        //is lid ingeschreven?
       if(l.getEindDatumLidmaatschap()!= null){
          throw new ApplicationException("lid is reeds uitgeschreven");
        }
       // heeft het lid nog openstaande ritten (nog te verwerken)?
         RitTrans rtrans = new RitTrans();
         Rit r = rtrans.zoekRit(Integer.valueOf(rr));
         if(r.getEindtijd()!= null){
            throw new ApplicationException("Er is minstens nog één open rit");
         }
             
        //lid uitschrijven
        LidDB ldb = new LidDB();
        ldb.uitschrijvenLid(rr);
        
    }

    /**
     * Zoek adhv aan rijksregisternummer een lid, wanneer er geen lid wordt gevonden ,
     * wordt een null geretourneerd.
     * 
     * @param rijksregisternummer rijksregisternummer van een lid die gezocht wordt, kan ook een null zijn.
     * @return het lid dat gevonden wordt of een null indien het lid niet werd gevonden
     * @throws Exception wordt opgegooid wanneer er geen rijksregister werd ingevoerd, door een fout in de query 
     * of een verkeerde installatatie van de database
     */
    
    @Override
   public Lid zoekLid(String rijksregisternummer) throws Exception{
       //parameter ingevuld?
       if(rijksregisternummer == null){
           throw new ApplicationException("Er werd geen rijksregisternummer ingevoerd");
       }
       // lid zoeken
       LidDB ldb = new LidDB();
       return ldb.zoekLid(rijksregisternummer);
       
   }
   
   /**
    * Geeft alle leden terug in een lijst.
    * 
    * @return lijst van alle leden , zowel in- als uitgeschreven
    * @throws Exception en wordt opgegooid bij een verkeerde installatie van de db 
    * of door een fout in de query
    */
    @Override
   public ArrayList<Lid> zoekAlleLeden() throws Exception{
       //alle leden zoeken
       LidDB ldb = new LidDB();
       return ldb.zoekAlleLeden();
     
   }
}





    
    
    


    

