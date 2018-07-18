
package databaseDB;


import databag.Rit;
import database.connect.ConnectionManager;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author Lamotte Tom
 */
public class RitDB implements InterfaceRitDB{
    
    /**
     * Toevoegen van een rit in de DB.
     * 
     * @param rit die moet worden toegevoegd
     * @return ritID van de toegevoegde rit
     * @throws DBException die duidt op een verkeerde installatie
     * van de database of een query fout
     */
    
    @Override
    public Integer toevoegenRit(Rit rit)throws DBException{
        if(rit != null){
            Integer key = null;
            try(Connection con = ConnectionManager.getConnection();){
                try(PreparedStatement ps = con.prepareStatement(
                        "insert into rit(id"
                        + ", starttijd"
                        + ", eindtijd"
                        + ", prijs"
                        + ", lid_rijksregisternummer"
                        + ", fiets_registratienummer"
                        + ")values(?,?,?,?,?,?)");)
                        {
                    //vermijden van NullPointerException maar niet van een DBException 
                    //omdat sommige velden verplich zijn
                    if(rit.getRitID()!= null){
                         ps.setInt(1,rit.getRitID());
                    }else
                        ps.setNull(1,java.sql.Types.NULL);
                    
                    if(rit.getStarttijd()!=null){
                        ps.setString(2,rit.getStarttijd().toString());
                    }else{
                        ps.setNull(2,java.sql.Types.NULL);
                    }
                     if(rit.getEindtijd()!=null){
                        ps.setString(3,rit.getStarttijd().toString());
                    }else{
                        ps.setNull(3,java.sql.Types.NULL);
                    }
                     if(rit.getPrijs()!= null){
                        ps.setBigDecimal(4, rit.getPrijs());
                    }else{
                        ps.setNull(4,java.sql.Types.NULL);
                    }
                    
                    if(rit.getRijksregisternummer() != null){
                        ps.setString(5, rit.getRijksregisternummer().toString());
                    }else{
                        ps.setNull(5,java.sql.Types.NULL);
                    }
                    
                    if(rit.getFietsregistratienummer() != 0){
                         ps.setInt(6, rit.getFietsregistratienummer());
                    }else{
                        ps.setNull(6,java.sql.Types.NULL);
                    }
                     ps.execute();    
                     ResultSet rs = ps.getResultSet();
                     if(rs.next()){
                         key = rs.getInt(1);
                     }
                    
        }catch (SQLException sqlEx){
            throw new DBException("SQL-exception in toevoegenRit "
                  + "- statement" + sqlEx);
                }
      }catch (SQLException sqlEx){
          throw new DBException("SQL-exception in toevoegenRit "
                + "- connection" + sqlEx);
      }
        return key;
        }else{
      return null;
    }
  }
    
    /**
     * Afsluiten van de meegegeven rit.
     * 
     * @param rit die moet worden afgesloten.
     * @throws DBException die duidt op een verkeerde installatie
     * van de database of een query fout
     */
    
    @Override
      public void afsluitenRit(Rit rit)throws DBException{
          if(rit != null){
              try(Connection con = ConnectionManager.getConnection();){
                  try(PreparedStatement ps = con.prepareStatement("update rit"
                          + " set eindtijd =?"
                          + " where id =?");){
                  ps.setString(1,rit.getEindtijd().toString());
                  ps.setInt(2,rit.getRitID());
                  ps.execute();
                  } catch (SQLException sqlEx){
                      throw new DBException("SQL-Exception in afsluitenRit - statement" +sqlEx);
                  }
              } catch (SQLException sqlEx){
                  throw new  DBException("SQL-Exception in afsluitenRit - connection" +sqlEx);
              }
          }
      }
      
      /**
       * Zoek alle ritten.
       * 
       * @return lijst van alle ritten
       * @throws DBException die duidt op een verkeerde installatie
       * van de database of een query fout
       * @throws ApplicationException die duidt op een foutief rekeningnummer
       */
      
      
    @Override
     public ArrayList zoekAlleRitten() throws DBException,ApplicationException,Exception{
     ArrayList<Rit> rit = new ArrayList<>();
     // connectie tot stand brengen (en automatisch sluiten)
       try (Connection conn = ConnectionManager.getConnection();) {
        // preparedStatement opstellen (en automatisch sluiten)
        try (PreparedStatement stmt = conn.prepareStatement(
              "select id"
              + " , starttijd"
              + " , eindtijd"
              + " , prijs"
              + " , lid_rijksregisternummer"
              + " , fiets_registratienummer"
              + "   from rit "
              + "   order by id");) {
            
        stmt.execute();
        // result opvragen (en automatisch sluiten)
        try (ResultSet r = stmt.getResultSet()) {
          // van alle ritten uit de database Rit-objecten maken
          // en in een lijst steken
          while (r.next()) {
            Rit rt = new Rit();
            // geen controle op null, id moet ingevuld zijn in DB
            rt.setRitID(r.getInt("id"));
            rt.setStarttijd(LocalDateTime.parse(r.getString("starttijd")));
            rt.setEindtijd(LocalDateTime.parse(r.getString("eindtijd")));
            rt.setPrijs(r.getBigDecimal("prijs"));
            rt.setRijksregisternummer(new Rijksregisternummer(r.getString("lid_rijksregisternummer")));
            rt.setFietsregistratienummer(r.getInt("fiets_registratienummer"));
            rit.add(rt);
          }
          return rit;
        } catch (SQLException sqlEx) {
          throw new DBException(
           "SQL-exception in zoekAlleRitten - resultset" + sqlEx);
        }
      } catch (SQLException sqlEx) {
          throw new DBException("SQL-exception in zoekAlleRitten - statement" + sqlEx);
      }
    } catch (SQLException sqlEx) {
        throw new DBException(
           "SQL-exception in zoekAlleRitten - connection" + sqlEx);
    }
  }
     
     /**
      * Zoeken van een rit aan de hand van een ID.
      * 
      * @param ritID het ID van de rit dat gezocht moet worden
      * @return de rit van het meegegeven ritID
      * @throws DBException die duidt op een verkeerde installatie
       * van de database of een query fout
       * @throws ApplicationException die duidt op een foutief ritID
      */


    @Override
 public Rit zoekRit(Integer ritID) throws DBException,ApplicationException,Exception{
      if(ritID != null){
          Rit rt = null;
          //connectie tot stand brengen en automatisch sluiten
          try(Connection conn = ConnectionManager.getConnection();){
            //preparedStatement opstellen (en automatisch sluiten)
                  try(PreparedStatement ps = conn.prepareStatement(
                          "select id"
                          + ", starttijd"
                          + ", eindtijd"
                          + ", prijs"
                          + ", lid_rijksregisternummer"
                          + ", fiets_registratienummer"
                          + "  from rit"
                          + "  where id = ?");){
                      ps.setInt(1,ritID);
                      ps.execute();
                      //result opvragen en automatisch afsluiten
                      try(ResultSet rs = ps.getResultSet()){
                          //van de rit uit de database een Rit-object maken
                          Rit r = new Rit();
                          // er werd een rit gevonden
                          
                          if(rs.next()){
                              r.setRitID(rs.getInt("id"));
                              r.setStarttijd(LocalDateTime.parse(rs.getString("starttijd")));
                              r.setEindtijd(LocalDateTime.parse(rs.getString("eindtijd")));
                              r.setPrijs(rs.getBigDecimal("prijs"));
                              r.setRijksregisternummer(new Rijksregisternummer(rs.getString("lid_rijksregisternummer")));
                              r.setFietsregistratienummer(rs.getInt("fiets_registratienummer"));
                              rt = r;
                          }
                           return rt;
                         } catch (SQLException sqlEx) {
                        throw new ApplicationException("SQL-exception in zoekRit - resultset" + sqlEx);
                    }
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekRit - statement" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException(
                        "SQL-exception in zoekRit - connection" + sqlEx);
            }
        } else {
            return null;
        }
    }
 
 /**
  * Zoeken van de eerste rit van een lid.
  * 
  * @param rr van de huurder die moet opgezocht worden
  * @return ritID van de gezochte rit.
  * @throws DBException die duidt op een verkeerde installatie
       * van de database of een query fout
  */
 
 
    @Override
   public int zoekEersteRitVanLid(String rr) throws DBException {
     
       if(rr != null){
          Integer ID=null;
          //connectie tot stand brengen en automatisch sluiten
          try(Connection conn = ConnectionManager.getConnection();){
            //preparedStatement opstellen (en automatisch sluiten)
                  try(PreparedStatement ps = conn.prepareStatement(
                          "select id"
                          + ", starttijd"
                          + ", eindtijd"
                          + ", prijs"
                          + ", lid_rijksregisternummer"
                          + ", fiets_registratienummer"
                          + "  from rit"
                          + "  where lid_rijksregisternummer = ?");){
                      ps.setString(1,rr);
                      ps.execute();
                      
                      try(ResultSet r = ps.getResultSet()){
                      if(r.next()){
                          ID = r.getInt(1);
                      }
                      
                    }catch (SQLException sqlEx) {
                        throw new DBException("SQL-exception in zoekEersteRitVanLid - resultset" + sqlEx);
                    }
                } catch (SQLException sqlEx) {
                    throw new DBException("SQL-exception in zoekEersteRitVanLid - statement" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException(
                        "SQL-exception in zoekEersteRitVanLid - connection" + sqlEx);
            } return (int)ID ;         
        
       }else{
           return 0;
       }
   }    
   
   /**
    * Zoeken naar actieve ritten van een lid.
    * 
    * @param rr van de huurder die opgezocht moet worden
    * @return een lijst van actieve ritten van een lid
    * @throws DBException die duidt op een verkeerde installatie
    * van de database of een query fout
    * @throws ApplicationException die duidt op een foutief rijksregisternummer
    */
 

    @Override
   public ArrayList zoekActieveRittenVanLid(String rr) throws DBException,ApplicationException,Exception{
       //connectie tot stand brengen en automatisch afsluiten
       if(rr != null){
       try(Connection conn  = ConnectionManager.getConnection();){
           ArrayList<Rit>rit = new ArrayList();
           //preparedStatement opstellen en automatisch afsluiten
           try(PreparedStatement ps = conn.prepareStatement(
                   "select id"
                  + ", starttijd"
                  + ", eindtijd"
                  + ", prijs"
                  + ", lid_rijksregisternummer"
                  + ", fiets_registratienummer"
                  + "  from rit"
                  + "  where eindtijd IS NULL"
                  + "  and lid_rijksregisternummer = ?");){
               
               ps.setString(1,rr);
               ps.execute();
               //result opvragen (en automatisch sluiten)
               
               try(ResultSet rs = ps.getResultSet()){
                   //van alle ritten uit de database Rit objecten maken en in een arraylist steken
                                      
                   while(rs.next()){
                       Rit r = new Rit();
                       r.setRitID(rs.getInt("id"));
                       r.setStarttijd(LocalDateTime.parse(rs.getString("starttijd")));
                       r.setEindtijd(LocalDateTime.parse(rs.getString("eindtijd")));
                       r.setPrijs(rs.getBigDecimal("prijs"));
                       r.setRijksregisternummer(new Rijksregisternummer(rs.getString("lid_rijksregisternummer")));
                       r.setFietsregistratienummer(rs.getInt("fiets_registratienummer"));
                       rit.add(r);
                   }
                   return rit;
               } catch (SQLException sqlEx){
                   throw new ApplicationException("SQL-exception in zoekActieveRittenVanLid - resultset" + sqlEx);
               }
           } catch(SQLException sqlEx){
                   throw new DBException("SQL-exception in zoekActieveRittenVanLid - statement" + sqlEx);
           }
       } catch(SQLException sqlEx){
           throw new DBException("SQL-exception in zoekActieveRitttenVanLid - connection" + sqlEx);
       }
   }else{ return null;}
   
   }
   
   /**
    * Zoeken naar actieve ritten van een fiets.
    * 
    * @param regnr die opgezocht moet worden
    * @return een lijst van actieve ritten van een fiets op basis van het meegegeven regnr
    * @throws DBException die duidt op een verkeerde installatie
    * van de database of een query fout
    * @throws ApplicationException die duidt op een foutief rijksregisternummer
    */
   
    @Override
    public ArrayList zoekActieveRittenVanFiets(Integer regnr) throws DBException,ApplicationException,Exception{
          //connectie tot stand brengen en automatisch afsluiten
           try(Connection conn  = ConnectionManager.getConnection();){
           ArrayList<Rit>rit = new ArrayList();
           //preparedStatement opstellen en automatisch afsluiten
           try(PreparedStatement ps = conn.prepareStatement(
                   "select id"
                  + ", starttijd"
                  + ", eindtijd"
                  + ", prijs"
                  + ", lid_rijksregisternummer"
                  + ", fiets_registratienummer"
                  + "  from rit"
                  + "  where eindtijd IS NULL"         
                  + "  and fiets_registratienummer = ?");){
              
               ps.setInt(1,regnr);
               ps.execute();
               //result opvragen (en automatisch sluiten)
               
               try(ResultSet rs = ps.getResultSet()){
                   //van alle ritten uit de database Rit objecten maken en in een arraylist steken
                                      
                   while(rs.next()){
                       Rit r = new Rit();
                       r.setRitID(rs.getInt("id"));
                       r.setStarttijd(LocalDateTime.parse(rs.getString("starttijd")));
                       r.setEindtijd(LocalDateTime.parse(rs.getString("eindtijd")));
                       r.setPrijs(rs.getBigDecimal("prijs"));
                       r.setRijksregisternummer(new Rijksregisternummer(rs.getString("lid_rijksregisternummer")));
                       r.setFietsregistratienummer(rs.getInt("fiets_registratienummer"));
                       rit.add(r);
                   }
                   return rit;
               } catch (SQLException sqlEx){
                   throw new ApplicationException("SQL-exception in zoekActieveRittenVanFiets -resultset" + sqlEx);
               }
           } catch(SQLException sqlEx){
                   throw new DBException("SQL-exception in zoekActieveRittenVanFiets -statement" + sqlEx);
           }
       } catch(SQLException sqlEx){
           throw new DBException("SQL-exception in zoekActieveRitttenVanFiets - connection" + sqlEx);
       }
           
   }
}
   
  



      





    
                    
