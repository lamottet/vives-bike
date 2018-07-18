
package databaseDB;


import databag.Lid;
import database.connect.ConnectionManager;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import java.sql.Connection;
import exception.DBException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * 
 * @author Lamotte Tom
 */


public class LidDB implements InterfaceLidDB{
    
        /**
         * Voegt meegegeven lid toe.
         * 
         * @param lid  lid dat moet worden toegevoegd
         * @throws DBException die duidt op een verkeerde installatie
         * van de database of een fout in de query
         */
       
      @Override
      public void toevoegenLid(Lid lid) throws DBException {
        if (lid != null) {
            // connectie tot stand brengen (en automatisch sluiten)
            try (Connection conn = ConnectionManager.getConnection();) {
                // preparedStatement opstellen (en automatisch sluiten)
                try (PreparedStatement stmt = conn.prepareStatement(
                                "insert into lid(rijksregisternummer"
                                + " , naam"
                                + " , voornaam"
                                + " , geslacht"
                                + " , telnr"
                                + " , emailadres"
                                + " , start_lidmaatschap"
                                + " , einde_lidmaatschap"
                                + " , opmerkingen"
                                + " )values(?,?,?,?,?,?,?,?,?)");) {

                    // vermijdt een NullpointerException maar geen DBException omdat sommige velden verplicht zijn om in te vullen
                    
                    if (lid.getRijksregisternummer() != null) {
                      stmt.setString(1, lid.getRijksregisternummer());
                    } else {
                      stmt.setNull(1, java.sql.Types.NULL);
                    }
                    if(lid.getNaam() != null){
                      stmt.setString(2,lid.getNaam());
                    } else {
                      stmt.setNull(2,java.sql.Types.NULL);
                    }
                    if(lid.getVoornaam() != null){
                      stmt.setString(3,lid.getVoornaam());
                    } else {
                      stmt.setNull(3,java.sql.Types.NULL);
                    }
                    if(lid.getGeslacht() != null){
                      stmt.setString(4,lid.getGeslacht().toString());
                    } else {
                      stmt.setNull(4,java.sql.Types.NULL);
                    } 
                    if(lid.getTelNr() != null){
                      stmt.setString(5,lid.getTelNr());
                    } else {
                      stmt.setNull(5,java.sql.Types.NULL);
                    }
                    if(lid.getEmail() != null){
                      stmt.setString(6,lid.getEmail());
                    } else {
                      stmt.setNull(6,java.sql.Types.NULL);
                    }
                     if (lid.getStartDatumLidmaatschap() != null) {
                      stmt.setString(7, lid.getStartDatumLidmaatschap().toString());
                    } else {
                      stmt.setNull(7, java.sql.Types.NULL);
                    }
                     if(lid.getEindDatumLidmaatschap()!= null){
                      stmt.setString(8,lid.getEindDatumLidmaatschap().toString());
                    } else {
                      stmt.setNull(8, java.sql.Types.NULL);
                    }
                    if(lid.getOpmerkingen() != null){
                      stmt.setString(9,lid.getOpmerkingen());
                    } else {
                      stmt.setNull(9,java.sql.Types.NULL);
                    }
               
                     stmt.execute();
                 
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in toevoegenLid - statement" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException(
                        "SQL-exception in toevoegenLid - connection" + sqlEx);
            }
        }
      }
      
      /**
       *  Wijzigt het lid aan de hand van zijn rijksregisternummer
       * 
       * @param lid lid dat moet gewijzigd worden
       * @throws DBException die duidt op een verkeerde installatie
       * van de database of een fout in de query 
       */
      
      @Override
      public void wijzigenLid(Lid lid) throws DBException {
        if (lid != null){
             //connectie tot stand brengen en automatish sluiten
            try(Connection con = ConnectionManager.getConnection();){
                //preparedStatement opstellen (en afsluiten)
                try(PreparedStatement ps = con.prepareStatement("update lid " 
                + " set rijksregisternummer = ?"
                + "   , voornaam = ?"
                + "   , naam = ?"        
                + "   , geslacht = ?"
                + "   , telnr = ?"
                + "   , emailadres = ?"
                + "   , start_lidmaatschap = ?"
                + "   , einde_lidmaatschap = ?"
                + "   , opmerkingen = ?" 
                + " where rijksregisternummer = ?");){
                    //NullPointerException vermijden maar geen DBException omdat 
                    //sommige velden verplicht moeten ingevuld worden
                    if(lid.getRijksregisternummer()!= null){
                        ps.setString(1,lid.getRijksregisternummer());
                    }else{
                        ps.setNull(1,java.sql.Types.NULL);
                    }
                    if(lid.getNaam() != null){
                        ps.setString(3,lid.getNaam());
                    }else{
                        ps.setNull(3,java.sql.Types.NULL);
                    }
                    if(lid.getVoornaam()!= null){
                        ps.setString(2,lid.getVoornaam());
                    } else {
                        ps.setNull(2,java.sql.Types.NULL);
                    }
                    if(lid.getGeslacht()!= null){
                        ps.setString(4,lid.getGeslacht().toString());
                    } else {
                        ps.setNull(4,java.sql.Types.NULL);
                    }
                     if(lid.getTelNr()!= null){
                        ps.setString(5,lid.getTelNr());
                    } else {
                        ps.setNull(5,java.sql.Types.NULL);
                    }
                    if(lid.getEmail()!= null){
                       ps.setString(6,lid.getEmail());
                    } else {
                        ps.setNull(6,java.sql.Types.NULL);
                    }
                      
                    if(lid.getStartDatumLidmaatschap()!=null){
                     ps.setDate(7,Date.valueOf(lid.getStartDatumLidmaatschap()));
                    }else{
                       ps.setNull(7, java.sql.Types.NULL);
                    }
                     if(lid.getEindDatumLidmaatschap()!=null){
                      ps.setDate(8,Date.valueOf(lid.getEindDatumLidmaatschap()));
                     }else{
                         ps.setNull(8,java.sql.Types.NULL);
                    }
                       
                     if(lid.getOpmerkingen()!= null){
                          ps.setString(9,lid.getOpmerkingen());
                    } else {
                          ps.setNull(9,java.sql.Types.NULL);
                    }
                    if(lid.getRijksregisternummer()!= null){
                        ps.setString(10,lid.getRijksregisternummer());
                    } else {
                        ps.setNull(10,java.sql.Types.NULL);
                    }
                 
                ps.execute();
                        
            } catch (SQLException sqlEx) {
                throw new DBException("SQL-exception in wijzigenLid - statement" + sqlEx);
            }
       } catch (SQLException sqlEx){
            throw new DBException ("SQL-exception in wijzigenLid - connection" + sqlEx);
         }
       }
      }
      
      /**
       * Zoek een lid adhv zijn rijksregisternummer
       * 
       * @param rijksregisternummer dat moet worden gezocht in de DB
       * @return het gevonden lid adhv het rijksregisternummer
       * @throws DBException die duidt op een verkeerde installatie
       * van de database of een fout in de query 
       * @throws ApplicationException die duidt op een foutief rijksregisternummer
       */
        
      @Override
 public Lid zoekLid(String rijksregisternummer) throws DBException, ApplicationException,Exception{
        if(rijksregisternummer!= null){
            Lid returnLid = null;
            //connectie tot strand brengen (en automatisch sluiten)
            try (Connection con = ConnectionManager.getConnection();){
                //preparedStatement opstellen( en automatisch sluiten)
                try(PreparedStatement ps = con.prepareStatement (
                       "select rijksregisternummer"
                        + " , naam"
                        + " , voornaam"
                        + " , geslacht"
                        + " , telnr"
                        + " , emailadres"
                        + " , start_lidmaatschap"
                        + " , einde_lidmaatschap"
                        + " , opmerkingen"
                        + " from lid "
                        + " where rijksregisternummer = ?");) {
                    
                    ps.setString(1,rijksregisternummer);
                    ps.execute();
                    //result opvragen ( en automatisch sluiten)
                    try (ResultSet r = ps.getResultSet()){
                       if (r.next()){
                            Lid l = new Lid();
                            l.setRijksregisternummer(new Rijksregisternummer(r.getString("rijksregisternummer"))); 
                            l.setNaam(r.getString("naam"));
                            l.setVoornaam(r.getString("voornaam")); 
                            l.setGeslacht(Geslacht.valueOf(r.getString("geslacht")));
                            l.setTelNr(r.getString("telnr"));
                            l.setEmail(r.getString("emailadres"));
                            l.setStartDatumLidmaatschap(LocalDate.parse(r.getString("start_lidmaatschap")));
                            l.setEindeDatumLidmaatschap(LocalDate.parse(r.getString("einde_lidmaatschap")));
                            l.setOpmerkingen(r.getString("opmerkingen"));
                            returnLid =l;
                        }
                        return returnLid;
                  } catch (SQLException sqlEx) {
                      throw new ApplicationException("SQL-exception in zoekLid - resultset" + sqlEx);
                  }
             } catch (SQLException sqlEx) {
                      throw new DBException("SQL-exception in zoekLid - statement" + sqlEx);
                  }
        } catch (SQLException sqlEx) {
                      throw new DBException("SQL-exception in zoekLid - connection" + sqlEx);
                  }
    } else {
    return null;
   }
}
     
 /**
  * Zoeken naar alle leden in de DB
  * 
  * @return een lijst van alle leden
  * @throws DBException die duidt op een verkeerde installatie
  * van de database of een fout in de query 
  * @throws ApplicationException die duidt op een foutief rijksregisternummer
  */
 
 
      @Override
      public ArrayList<Lid> zoekAlleLeden() throws DBException,ApplicationException,Exception{
        ArrayList<Lid> lid = new ArrayList<>();
        //connectie tot stand brengen (en automatisch sluiten)
        try(Connection con  = ConnectionManager.getConnection();){
             try (PreparedStatement stmt = con.prepareStatement(
              "select rijksregisternummer"
              + " , voornaam"        
              + " , naam"
              + " , geslacht"
              + " , telnr"
              + " , emailadres"
              + " , start_lidmaatschap"
              + " , einde_lidmaatschap"
              + " , opmerkingen"
              + " from lid ");) {
                         
              stmt.execute();
            
           try (ResultSet r = stmt.getResultSet()) {
            // van alle leden uit de database Lid-objecten maken
            // en in een lijst steken
           while (r.next()) {
           Lid l = new Lid();
            // geen controle op null, id moet ingevuld zijn in DB
          
           l.setRijksregisternummer(new Rijksregisternummer(r.getString("rijksregisternummer")));
           l.setNaam(r.getString("naam"));
           l.setVoornaam(r.getString("voornaam"));
           l.setGeslacht(Geslacht.valueOf(r.getString("geslacht")));
           l.setTelNr(r.getString("telnr"));
           l.setEmail(r.getString("emailadres"));
           l.setStartDatumLidmaatschap(LocalDate.parse(r.getString("start_lidmaatschap")));
           l.setEindeDatumLidmaatschap(LocalDate.parse(r.getString("einde_lidmaatschap")));
           l.setOpmerkingen(r.getString("opmerkingen"));
           lid.add(l);
          }
          return lid;
        } catch (SQLException sqlEx) {
             throw new ApplicationException("SQL-exception in zoekAlleLeden - resultset" + sqlEx);
        }
     } catch (SQLException sqlEx) {
        throw new DBException("SQL-exception in zoekAlleLeden - statement" + sqlEx);
     }
   } catch (SQLException sqlEx) {
      throw new DBException( "SQL-exception in zoekAlleLeden - connection" + sqlEx);
        
     } 
   }
      /**
       * Schrijft het lid met het meegegeven rr (rijksregisternummer) uit.
       * 
       * @param rr van het lid dat moet uitgeschreven worden
       * @throws DBException die duidt op een verkeerde installatie
       * van de database of een fout in de query
       */
      
      @Override
       public  void uitschrijvenLid(String rr) throws DBException {
           if (rr != null){
               //connectie tot stand brengen (en automatisch sluiten)
              try (Connection con = ConnectionManager.getConnection();){
               //preparedStatement opstellen (en automatisch sluiten)
               try(PreparedStatement st = con.prepareStatement(
                         "update lid"
                        + "set einde_lidmaatschap=?"
                        + "where rijksregisternummer=?" );){
                     
               st.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
               st.setString(2,rr);
               st.execute();
               
             }catch (SQLException sqlEx) {
          throw new DBException("SQL-exception in uitschrijvenLid - statement" + sqlEx);
          }
        }catch (SQLException sqlEx) {
        throw new DBException(
                "SQL-exception in uitschrijvenLid - connection" + sqlEx);
      }
    }
  }
}
  
                   
                 
                  
                

   
    
