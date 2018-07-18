/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactie;

import databag.Fiets;
import databag.Lid;
import databag.Rit;
import databaseDB.RitDB;
import exception.ApplicationException;
import java.util.ArrayList;

/**
 *
 * @author Lamotte Tom
 */
public class RitTrans implements InterfaceRitTrans{
    
    @Override
    public Integer toevoegenRit(Rit rit) throws Exception{
        //is er een parameter ingevuld?
        if(rit == null){
            throw new ApplicationException("Er werd geen rit ingevoerd");
        }
        //zijn alle ritgegevens ingevuld?
        checkVeldenIngevuld(rit);
        
        //bestaat de rit?
        if(zoekRit(rit.getRitID())!=null){
            throw new ApplicationException("Deze rit bestaat al");
        
        }
        //bestaat het lid?
        LidTrans lt = new LidTrans();
        Lid l = lt.zoekLid(rit.getRijksregisternummer().toString());
        if (l == null){
            throw new ApplicationException("Lid bestaat niet");
            
        }
        //is het lid nog ingeschreven?
        if(l.getEindDatumLidmaatschap()!= null){
            throw new ApplicationException("Lid niet ingeschreven");
        }
        //toevoegen
        RitDB rdb = new RitDB();
        return rdb.toevoegenRit(rit);
    }
    
    
    public void checkVeldenIngevuld(Rit r)throws ApplicationException {
        if(r.getRitID()== null){
        throw new ApplicationException("Ritnummer niet ingevuld");
    }
        if(r.getStarttijd()==null){
        throw new ApplicationException("Starttijd niet ingevuld");
    }
        if(r.getPrijs()==null){
        throw new ApplicationException("Prijs niet ingevuld");
    }
        if(r.getRijksregisternummer()== null){
         throw new ApplicationException("Lidrijksregisternummer niet ingevuld");
    }
  }
    
    @Override
     public void afsluitenRit(Integer id) throws Exception{
         //parameter ingevuld?
         if(id != null){
             throw new ApplicationException("Er werd geen id ingevuld");
         }
        //bestaat de rit?
        Rit r = zoekRit(id);
         if(r == null){
             throw new ApplicationException("Rit niet gevonden");
         }
         //is de rit reeds afgesloten?
         if(r.getEindtijd()!= null){
            throw new ApplicationException("De rit is reeds afgesloten");
         }
         //afsluiten
         RitDB rdb = new RitDB();
         rdb.afsluitenRit(r);
                 
     }
     
    @Override
       public ArrayList zoekAlleRitten() throws Exception{
           RitDB rdb = new RitDB();
           return rdb.zoekAlleRitten();
      }
        
     
     
    @Override
   public Rit zoekRit(Integer ritID) throws Exception{
       //parameter ingevuld?
       if(ritID == null){
           throw new ApplicationException("Er werd geen ritID ingevoerd");
       }
       // rit zoeken
       RitDB rdb = new RitDB();
       return rdb.zoekRit(ritID);
   } 
   
   
    @Override
    public Integer zoekEersteRitVanLid(String rr) throws Exception{
        //parameter ingevuld?
        if(rr == null){
            throw new ApplicationException("Er werd geen lidrijksregisternummer ingegeven");
        }   
       //bestaat het lid?
       LidTrans lt = new LidTrans();
       Lid lid = lt.zoekLid(rr);
       if(lid == null){
           throw new ApplicationException("Lid is niet gevonden");
       }
       //eerste rit zoeken
       RitDB rdb = new RitDB();
       return rdb.zoekEersteRitVanLid(rr);
    }
       
       

    @Override
    public ArrayList zoekActieveRittenVanLid(String rr) throws Exception{
        //parameter ingevuld?
        if(rr == null){
            throw new ApplicationException("Er werd geen lidrijksregisternummer ingegeven");
        }
        //bestaat het lid?
       LidTrans lt = new LidTrans();
       Lid lid = lt.zoekLid(rr);
            if(lid == null){
                throw new ApplicationException("Het lid is niet gevonden");
       }
       //zoeken van actieve ritten van lid
       RitDB rdb = new RitDB();
       return rdb.zoekActieveRittenVanLid(rr);
    }
       
    @Override
    public ArrayList zoekActieveRittenVanFiets(Integer regnr) throws Exception{
        //parameter ingevuld?
        if(regnr == null){
            throw new ApplicationException("Er werd geen fietsregistratienummer ingegeven");
        }
        //bestaat de fiets?
       FietsTrans ft = new FietsTrans();
       Fiets fiets = ft.zoekFiets(regnr);
            if(fiets == null){
                throw new ApplicationException("De fiets is niet gevonden");
       }
       //zoeken van actieve ritten van fiets
       RitDB rdb = new RitDB();
       return rdb.zoekActieveRittenVanFiets(regnr);
    }
}

 
     
