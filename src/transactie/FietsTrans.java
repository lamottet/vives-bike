
package transactie;

import databag.Fiets;
import databaseDB.FietsDB;
import datatype.Status;
import exception.ApplicationException;
import java.util.ArrayList;

/**
 *
 * @author Lamotte Tom
 */
public class FietsTrans implements InterFaceFietsTrans{
    /**
     * Voegt een fiets toe.
     * 
     * @param fiets die moet worden toegevoegd
     * @return fietsregistratienummer van de pas toegevoegde fiets
     * @throws Exception wordt gegooid wanneer geen fiets werd opgegeven, wanneer niet alle velden (correct) ingevuld zijn,
     * wanneer er al een fiets bestaat of wanneer er fouten zijn vanuit de DB
     * 
     */
    
    @Override
    public Integer toevoegenFiets(Fiets fiets)throws Exception{
        //is er een parameter ingevuld?
        if(fiets == null){
            throw new ApplicationException("Er is geen fiets ingevuld");
        }
        
        //zijn alle gegevens van het lid ingevuld?
            checkVeldenIngevuld(fiets);
            
        //bestaat de fiets?
            if(zoekFiets(fiets.getRegistratienummer()) != null){
                throw new ApplicationException("Het lid bestaat reeds");
        }
            
        //toevoegen fiets
            FietsDB fdb = new FietsDB();
            return fdb.toevoegenFiets(fiets);
            
    }
    
    /**
     * Controleert of alle velden in het object f ingevuld zijn
     * Gooit een exception bij: - Registratienummer niet ingevuld - standplaats niet ingevuld -
     * status niet ingevuld en status moet actief of in_herstel hebben - opmerking niet ingevuld
     */
         
    private void checkVeldenIngevuld(Fiets f) throws Exception{
    if(f.getRegistratienummer()== null||Integer.toString(f.getRegistratienummer()).equals("")){
        throw new ApplicationException("Registratienummer niet ingevuld");
    }
        if(f.getStandplaats()==null){
        throw new ApplicationException("Standplaats niet ingevuld");
    }
    if(f.getStatus()==null||f.getStatus().equals(Status.UIT_OMLOOP)){
        throw new ApplicationException("Fiets moet status ACTIEF of IN_HERSTEL krijgen");
    }
    
    if(f.getOpmerking() == null||f.getOpmerking().trim().equals("")){
         throw new ApplicationException("Opmerking niet ingevuld");
       }
    }
    
    /** Wijzigt de status van de fiets van ACTIEF naar IN_HERSTEL op basis van het registratienummer.
     * 
     * @param regnr fiets wordt opgezocht in DB adhv regnr
     * @throws Exception wordt gegooid wanneer er geen
     * fiets werd opgegeven,
     * wanneer de fiets niets bestaat met dat regnr, wanneer de fiets al uit omloop is,
     * wanneer de fiets al in_herstel modus is,
     * wanneer er fouten in de DB zijn.
     */
      
    @Override
   public void wijzigenActiefNaarHerstel(Integer regnr) throws Exception{
        //parameter ingevuld?
   if( regnr == null){
       throw new ApplicationException("Er is geen fietsregistratienummer ingevuld");
   }
   // bestaat het registratienummer en dus de fiets in de DB?
   Fiets f = zoekFiets(regnr);
   if(f == null){
      throw new ApplicationException("Fiets is niet gevonden"); 
   }
    if(f.getStatus().equals(Status.IN_HERSTEL)){
        throw new ApplicationException("Fiets is reeds in herstel modus");
    }
   //Is de fiets reeds uit omloop?
    if(f.getStatus().equals(Status.UIT_OMLOOP)){
        throw new ApplicationException("Fiets is reeds uit omloop");
    }
    //Fiets status aanpassen 
    if(f.getStatus().equals(Status.ACTIEF)){
    FietsDB fdb = new FietsDB();
    fdb.wijzigenToestandFiets(regnr,Status.IN_HERSTEL);
        }
   }
  /* 
   /**
    * Wijzigt de status van de fiets van ACTIEF naar UIT_OMLOOP op basis van het registratienummer
    * 
    * @param regnr gegevens van fiets die gewijzigd moet worden
    * @throws Exception wordt gegooid wanneer er geen fiets werd opgegeven,
    * wanneer de fiets niets bestaat met dat regnr, wanneer de fiets al uit omloop is,
    * wanneer de fiets al in_herstel modus is,
    * wanneer er fouten in de DB zijn
    */
   
    @Override
    public void wijzigenActiefNaarUitOmloop(Integer regnr) throws Exception{
    //parameter ingevuld?
     if(regnr == null){
       throw new ApplicationException("Er is geen fietsregistratienummer ingevuld");
     }
   // bestaat het registratienummer en dus de fiets?
    Fiets f = zoekFiets(regnr);
     if(f == null){
      throw new ApplicationException("Fiets is niet gevonden");
    }
    //Is de fietsstatus reeds in herstel?
   
    if(f.getStatus().equals(Status.IN_HERSTEL)){
        throw new ApplicationException("Fiets is reeds in herstel modus");
    }
    //Is de fiets reeds uit omloop?
   
    if(f.getStatus().equals(Status.UIT_OMLOOP)){
        throw new ApplicationException("Fiets is reeds uit omloop");
    }
    //Fiets status aanpassen 
    
    if(f.getStatus().equals(Status.ACTIEF)){
    FietsDB fdb = new FietsDB();
    fdb.wijzigenToestandFiets(regnr, Status.UIT_OMLOOP);
              }
    }
    /**
    * Wijzigt de status van de fiets van IN_HERSTEL naar ACTIEF op basis van het registratienummer
    * 
    * @param regnr gegevens van fiets die gewijzigd moet worden
    * @throws Exception wordt gegooid wanneer er geen
    * fiets werd opgegeven,wanneer de fiets niets bestaat op basis van regnr,
    * wanneer de fiets al uit omloop is, wanneer de fiets al in actief modus is,
    * wanneer er fouten in de DB zijn
    */
    
    @Override
    public void wijzigenHerstelNaarActief(Integer regnr) throws Exception{
    //parameter ingevuld?
    if(regnr == null){
            throw new ApplicationException("Er is geen fietsregistratienummer ingevuld");
    }
      
    //bestaat het fietsregistratienummer en dus ook de fiets?
    Fiets f = zoekFiets(regnr);
    if(f == null){
      throw new ApplicationException("Fiets is niet gevonden");
    }
    // is de fiets reeds in actieve toestand?
    
    if(f.getStatus().equals(Status.ACTIEF)){
        throw new ApplicationException("Fiets is reeds actief");
    }
    //is de fiets reeds uit omloop?
    
    if(f.getStatus().equals(Status.UIT_OMLOOP)){
        throw new ApplicationException("Fiets is uit omloop en kan niet meer actief worden");
    }
    //fiets status aanpassen
   
    if(f.getStatus().equals(Status.IN_HERSTEL)){
    FietsDB fdb = new FietsDB();
    fdb.wijzigenToestandFiets(regnr, Status.ACTIEF);  
        
         }
    }
     /**
    * Wijzigt de status van de fiets van IN_HERSTEL naar UIT_OMLOOP op basis van het registratienummer
    * 
    * @param regnr gegevens van fiets die gewijzigd moet worden
    * @throws Exception wordt gegooid wanneer er geen
    * fiets werd opgegeven,
    * wanneer de fiets niets bestaat op basis van regnr,
    * wanneer de fiets al uit omloop is, wanneer de fiets al in_herstel modus is,
    * wanneer er fouten in de DB zijn
    */
    
    
    @Override
    public void wijzigenHerstelNaarUitOmloop(Integer regnr) throws Exception{
        
    //parameter ingevuld?
    if(regnr == null){
            throw new ApplicationException("Er is geen fietsregistratienummer ingevuld");
    }
    //bestaat het fietsregistratie en dus ook de fiets?
    Fiets f = zoekFiets(regnr);
    if(f == null){
      throw new ApplicationException("Fiets is niet gevonden");
    }
    // is de fiets in een actieve toestand?
    
    if(f.getStatus().equals(Status.ACTIEF)){
        throw new ApplicationException("Fiets is actief");
    }
    //is de fiets uit omloop?
    
    if(f.getStatus().equals(Status.UIT_OMLOOP)){
        throw new ApplicationException("Fiets is uit omloop");
    }
    //fiets status aanpassen
    
    if(f.getStatus().equals(Status.IN_HERSTEL)){
    FietsDB fdb = new FietsDB();
    fdb.wijzigenToestandFiets(regnr, Status.UIT_OMLOOP);  
   
        }
    }
    /**
     * Wijzigen van de opmerking fiets op basis van zijn registratienummer
     * 
     * @param regnr van de fiets die moet opgezocht worden
     * @param opmerking van de fiets die moet gewijzigd worden
     * @throws Exception wordt gegooid wanneer er geen fiets wordt ingegeven, 
     * wanneer de fiets niet bestaat, wanneer de fiets uit omloop is en dus
     * niet meer beschikbaar is
     */  
    
    
    @Override
    public void wijzigenOpmerkingFiets(Integer regnr, String opmerking) throws Exception{
        //parameters ingevuld?
        if(regnr == null || opmerking == null){
            throw new ApplicationException("fietsnummer en/of opmerking niet ingevuld");
        }
       //bestaat de fiets?
       Fiets f = zoekFiets(regnr);
       if(f == null){
           throw new ApplicationException("fiets is niet gevonden");
       }
       //is de fiets nog in omloop?
       
       if(f.getStatus().equals(Status.UIT_OMLOOP)){
           throw new ApplicationException("fiets is niet meer beschikbaar");
       }
       //fietsopmerking wijzigen
       FietsDB fdb = new FietsDB();
       fdb.wijzigenOpmerkingFiets(regnr, opmerking);
    }
    /**
     * Zoeken naar een fiets adhv registratienummer
     * 
     * @param registratienummer van de fiets die moet gezocht worden
     * @return de gezochte fiets uit de DB adhv het registratienummer
     * @throws Exception wanneer er geen registratienummer is opgegeven
     */
       
    @Override
    public Fiets zoekFiets(Integer registratienummer) throws Exception{
        if(registratienummer == null){
            throw new ApplicationException("er werd geen registratienummer opgegeven");
        }
        FietsDB fdb = new FietsDB();
        return fdb.zoekFiets(registratienummer);
    
    }
    /**
     * Zoek alle Fietsen uit de DB
     * @return een lijst van alle fietsen uit de DB
     * @throws Exception 
     */

    @Override
      public ArrayList<Fiets> zoekAlleFietsen() throws Exception{
      FietsDB fdb = new FietsDB();
      return fdb.zoekAlleFietsen();
      
    }

}
