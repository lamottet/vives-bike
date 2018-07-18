
package databaseDB;

import databag.Fiets;
import database.connect.ConnectionManager;
import datatype.Standplaats;
import datatype.Status;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tom Lamotte
 */

public class FietsDB implements InterfaceFietsDB{
    /**
     * Voegt meegegeven fiets toe.
     * 
     * @param fiets die moet worden toegevoegd
     * @return registratienummer als primaryKey
     * @throws DBException die duidt op een verkeerde
     * installatie van de database of een fout in de query
     */
    
    @Override
    public Integer toevoegenFiets(Fiets fiets) throws DBException{
        if(fiets != null){
            Integer primaryKey = null;
         // connectie tot stand brengen (en automatisch sluiten)
      try (Connection conn = ConnectionManager.getConnection();) {
        // preparedStatement opstellen (en automatisch sluiten)
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into fiets(registratienummer"
                + " , status"
                + " , standplaats"
                + " , opmerkingen "
                + " )  values(?,?,?,?)");){
            //vermijden van NullpointerException
            //maar levert wel een DBException op aangezien registratienummer een verplicht veld is
            if(fiets.getRegistratienummer() != null) {
                stmt.setInt(1,fiets.getRegistratienummer());
            } else {
                stmt.setNull(1,java.sql.Types.NULL);
            }
            
            if(fiets.getStatus() != null) {
                stmt.setString(2,Status.ACTIEF.toString());
            } else {
                stmt.setNull(2,java.sql.Types.NULL);
            }
            if(fiets.getStandplaats() != null) {
                stmt.setString(3,fiets.getStandplaats().toString());
            } else {
                stmt.setNull(3,java.sql.Types.NULL);
            }
            
            if(fiets.getOpmerking() != null){
                stmt.setString(4,fiets.getOpmerking());
            } else {
                stmt.setNull(4,java.sql.Types.NULL);
            }
            stmt.execute();
         
        }catch (SQLException sqlEx){
          throw new DBException("SQL-exception in toevoegenFiets"
                  + "- statement" + sqlEx);
        }
        
      }catch (SQLException sqlEx){
        throw new DBException("SQL-exception in toevoegenFiets"
                + "- connection" + sqlEx);
      }
       return primaryKey;
    } else {
      return null;
    }
  }
    
    /**
     * Wijzigt een toestand van een fiets.
     * 
     * @param regnr die moet opgezocht worden
     * @param status die moet gewijzigd worden
     * @throws DBException die duidt op een verkeerde
     * installatie van de database of een fout in de query
     */
  
    @Override
    public void wijzigenToestandFiets(Integer regnr, Status status) throws DBException{

    //connectie tot stand brengen (en automatisch sluiten)
   try(Connection con  = ConnectionManager.getConnection();){
       //preparedStatement opstellen (en automatisch sluiten)
       try(PreparedStatement ps = con.prepareStatement(
                 "update fiets"
               + "set status"
               + "where registratienummer=?"
               + "and status=?");){  
           //vermijden van NullPointerException
           //maar levert wel een DBException op
           //aangezien registratienummer een verplicht veld is
           
            if(regnr != null){
                ps.setInt(1,regnr);
            }else{
                ps.setNull(1, java.sql.Types.NULL);
            }
            if(status != null){
                ps.setString(2,status.toString());
            }else{
                ps.setNull(2,java.sql.Types.NULL);
            }
                ps.execute();
       
         }catch(SQLException sqlEx){
             throw new DBException("SQL-exception in wijzigenToestandfiets-statement" +sqlEx);
         }
   }catch(SQLException sqlEx){
       throw new DBException("SQL-exception in wijzigenToestandFiets-connection" +sqlEx);
   }
  } 
    /**
     *  Wijzigt een opmerking van een fiets.
     * 
     * @param regnr die moet opgezocht worden
     * @param opmerking die moet gewijzigd worden
     * @throws DBException die duidt op een verkeerde
     * installatie van de database of een fout in de query
     */

    @Override
    public void wijzigenOpmerkingFiets(Integer regnr, String opmerking) throws DBException{
        //connectie tot stand brengen (en automatisch sluiten)
       try(Connection con  = ConnectionManager.getConnection();){
          //preparedStatement opstellen (en automatisch sluiten)
          try(PreparedStatement ps = con.prepareStatement(
                "update fiets"
              + "set opmerkingen"
              + "where registratienummer =?"
              + "and opmerkingen=?");){ 
              if(regnr!= null){
                  ps.setInt(1,regnr);
              }else{
                  ps.setNull(1,java.sql.Types.NULL);
              }
              if(opmerking!=null){
                  ps.setString(2,opmerking);
              }else{
                  ps.setNull(2,java.sql.Types.NULL);
              }
        
              ps.execute();
         }catch(SQLException sqlEx){
             throw new DBException("SQL-exception in wijzigenOpmerkingfiets" + sqlEx);
          }
   }catch(SQLException sqlEx){
       throw new DBException("SQL-exception in wijzigenOpmerkingFiets-connection" + sqlEx);
   }
  }
    
  /**
   * Zoekt aan de hand van het registratienummer een fiets op. Wanneer geen fiets werd gevonden
   * wordt null teruggegeven.
   *  
   * @param regnr registratienummer van de fiets die gezocht moet worden
   * @return fiets die gezocht wordt, null indien de fiets niet werd gevonden
   * @throws DBException die duidt op een verkeerde installatie van de database of een fout in de query
   * 
   */
       

    @Override
 public Fiets zoekFiets(Integer regnr) throws DBException{
     if(regnr != null) {
         Fiets returnFiets = null;
         //connectie tot stand brengen( en automatisch sluiten)
         try(Connection con = ConnectionManager.getConnection();){
             //preparedStatement opstellen (en automatisch sluiten)
             try(PreparedStatement ps = con.prepareStatement("select registratienummer"
                     + " , status"
                     + " , standplaats"
                     + " , opmerkingen"
                     + "  from fiets"
                     + "  where registratienummer=?");){
                 ps.setInt(1,regnr);
                 ps.execute();
                 //result opvragen (en automatisch sluiten)
                 try(ResultSet r = ps.getResultSet()){
                     //van de fiets uit de database een Fiets-object maken
                     Fiets f = new Fiets();
                     //er werd een registratienummer gevonden
                     if(r.next()){
                         f.setRegistratienummer(r.getInt("registratienummer")); 
                         f.setStatus(Status.valueOf(r.getString("status")));
                         f.setStandplaats(Standplaats.valueOf(r.getString("standplaats")));
                         f.setOpmerking(r.getString("opmerkingen"));
                         returnFiets = f;
                     }
                     return returnFiets;
                    }catch(SQLException sqlEx){
                             throw new DBException("SQL-exception in zoekFiets - resultset" + sqlEx);
                             }
                 }catch(SQLException sqlEx){
                     throw new DBException("SQL-exception in zoekFiets - statement" + sqlEx);
                             }
         }catch(SQLException sqlEx){
             throw new DBException("SQL-exception in zoekFiets - connection" + sqlEx);
         }
     }else{
         return null;
     }
}
 
/**
 * 
 * Geeft alle fietsen gesorteerd op registratienummer.
 * 
 * @return lijst van alle fietsen
 * @throws DBException die duidt op een verkeerde installatie van de database of een fout in de query
 */
 
    @Override
    public ArrayList<Fiets> zoekAlleFietsen() throws DBException{
         // connectie tot stand brengen (en automatisch sluiten)
        try (Connection conn = ConnectionManager.getConnection();) {
            ArrayList<Fiets> fiets = new ArrayList<>();
            // preparedStatement opstellen (en automatisch sluiten)
            try (PreparedStatement stmt = conn.prepareStatement(
                    "select registratienummer"
                    + " , status"
                    + " , standplaats"
                    + " , opmerkingen "
                    + " from fiets "
                    + " order by registratienummer");) {
                 stmt.execute();
                // result opvragen (en automatisch sluiten)
                try (ResultSet rs = stmt.getResultSet()) {

                    // van alle fietsen uit de database,
                    // Fietsen-objecten maken en in een lijst steken
                    while (rs.next()) {
                        Fiets f = new Fiets();
                        //geen controle op null, registratienummer moet ingevuld zijn in DB
                        f.setRegistratienummer(rs.getInt("registratienummer"));
                        f.setStatus(Status.valueOf(rs.getString("status")));
                        f.setStandplaats(Standplaats.valueOf(rs.getString("standplaats")));
                        f.setOpmerking(rs.getString("opmerkingen"));
                        fiets.add(f);
                    }
                    return fiets;
                } catch (SQLException sqlEx) {
                    throw new DBException(
                            "SQL-exception in zoekAlleFietsen - resultset" + sqlEx);
                }
            } catch (SQLException sqlEx) {
                throw new DBException(
                        "SQL-exception in zoekAlleFietsen - statement" + sqlEx);
            }
        } catch (SQLException sqlEx) {
            throw new DBException(
                    "SQL-exception in zoekAlleFietsen - connection" + sqlEx);
        }
    }
    
}


 





         